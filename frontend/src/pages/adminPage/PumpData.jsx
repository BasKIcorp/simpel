import React, { useState, useEffect } from 'react';
import {server_url} from "../../config"
import styles from './AdminPage.module.css'
import {useSelector} from "react-redux";
import {useDispatch} from "react-redux";
import EngineData from './EngineData'


const PumpData = ({ installationData, setInstallationData, isPump}) => {
    const [availablePumps, setAvailablePumps] = useState([]);
    const [availableMaterials, setAvailableMaterial] = useState([]);

    const dispatch = useDispatch();

    useEffect(() => {
        const loadOptions = async () => {
            let pumps;
            if (installationData.typeInstallations && installationData.subtype) {
                // Загружаем насосы по типу и подтипу
                pumps = await fetchInstallationPumps(installationData.typeInstallations, installationData.subtype);
            } else {
                // Загружаем все насосы
                pumps = await fetchAllPumps();
            }
            const materials = await fetchAllMaterial();

            if (pumps) setAvailablePumps(pumps);
            if (materials) setAvailableMaterial(materials);
        };
        loadOptions();
    }, [installationData.typeInstallations, installationData.subtype]);

    const handleChangePumpFields = (e,index) => {
        const { name, value } = e.target;
        setInstallationData(prevData => {
            const updatedPumps = [...prevData.pumps];
            updatedPumps[index] = {
                ...updatedPumps[index],
                [name]: value,
                name: ''
            };
            return {...prevData, pumps: updatedPumps};
        });
    };


    const handlePumpSelection = async (index, value) => {
        const selectedPump = availablePumps.find(pump => pump.name.toLowerCase() === value.toLowerCase());

        if (selectedPump) {
            // Получаем полные данные насоса по ID
            const pumpDetails = await fetchPumpById(selectedPump.id);

            if (pumpDetails) {
                setInstallationData(prevData => {
                    const updatedPumps = [...prevData.pumps];
                    updatedPumps[index] = { ...pumpDetails };
                    const updatedEngines = [...prevData.engines];
                    updatedEngines[index] = {...pumpDetails.engine}
                    const updatedPumpIds = [...prevData.pumpIds];
                    updatedPumpIds[index] = pumpDetails.id;
                    return {
                        ...prevData,
                        pumpIds: updatedPumpIds,
                        pumps: updatedPumps,
                        engines: updatedEngines
                    };
                });
            }
        } else {
            // Ввод вручную, сохраняем как текст
            setInstallationData(prevData => {
                const updatedPumps = [...prevData.pumps];
                updatedPumps[index] = {
                    ...updatedPumps[index],
                    name: value
                };
                const updatedPumpIds = [...prevData.pumpIds];
                updatedPumpIds[index] = "";
                return {
                    ...prevData,
                    pumpIds: updatedPumpIds,
                    pumps: updatedPumps
                };
            });
        }
    };




    const fetchAllPumps = async () => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            const response = await fetch(`${server_url}/api/simple/admin/pumps`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
            if (!response) {
                dispatch({ type: 'remove_user' });
                console.log('Нет ответа от сервера, токен удалён');
                return null;
            }
            if (response.status === 401 || response.status === 403) {
                alert("Токен испорчен")
                dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора
                if (response.status === 401) {
                    console.log('Испорченный токен');
                } else {
                    console.log('Нет прав для выполнения операции');
                }

                return null; // Завершаем выполнение
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Не удалось получить список насосов:", error);
            return null;
        }
    };

    const fetchPumpById = async (id) => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            const response = await fetch(`${server_url}/api/simple/admin/pumps/${id}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });console.log(response.status)
            if (!response) {
                dispatch({ type: 'remove_user' });
                console.log('Нет ответа от сервера, токен удалён');
                return null;
            }

            // Проверяем, если статус 401 или 403 (неавторизован или нет прав)
            if (response.status === 401 || response.status === 403) {
                alert("Токен испорчен")
                dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора

                if (response.status === 401) {
                    console.log('Испорченный токен');
                } else {
                    console.log('Нет прав для выполнения операции');
                }

                return null; // Завершаем выполнение
            }
            console.log(response)
            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Не удалось получить данные насоса:", error);
            return null;
        }
    };

    const fetchInstallationPumps = async (type, subType) => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            const response = await fetch(`${server_url}/api/simple/admin/instPump?type=${type}&subType=${subType}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
            console.log(response.status)
            if (!response) {
                dispatch({ type: 'remove_user' });
                console.log('Нет ответа от сервера, токен удалён');
                return null;
            }
            // Проверяем, если статус 401 или 403 (неавторизован или нет прав)
            if (response.status === 401 || response.status === 403) {
                alert("Токен испорчен")
                dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора

                if (response.status === 401) {
                    console.log('Испорченный токен');
                } else {
                    console.log('Нет прав для выполнения операции');
                }

                return null; // Завершаем выполнение
            }
            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Не удалось получить данные для установки насосов:", error);
            return null;
        }
    };
    const showSecondPump = ((installationData.subtype === "AFEIJP") && isPump) ;
    const fetchAllMaterial = async () => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            console.log(token)
            const response = await fetch(`${server_url}/api/simple/admin/materials`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
            if (!response) {
                dispatch({ type: 'remove_user' });
                console.log('Нет ответа от сервера, токен удалён');
                return null;
            }
            if (response.status === 401 || response.status === 403) {
                alert("Токен испорчен")
                dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора
                if (response.status === 401) {
                    console.log('Испорченный токен');
                } else {
                    console.log('Нет прав для выполнения операции');
                }

                return null; // Завершаем выполнение
            }
            console.log(response.body)
            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }
            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Не удалось получить список материалов:", error);
            return null;
        }
    }

    const handleChangeMaterial = (index, selectedMaterial) => {
        setInstallationData((prevData) => {
            const updateMaterial = [...prevData.material];
            updateMaterial[index] = selectedMaterial;
            return {...prevData,
                material: updateMaterial};
        });
    };

    return (
        <div className={styles.selectWrapper}>
        <div className={styles.pumpContainer}>
            {/* Левый насос */}
            <div className={`${styles.pumpSection} ${styles.pumpLeft}`}>
                <PumpEngineFields
                    index={0}
                    availablePumps={availablePumps}
                    availableMaterials={availableMaterials}
                    handleChangeMaterial={handleChangeMaterial}
                    handlePumpSelection={handlePumpSelection}
                    installationData={installationData}
                    setInstallationData={setInstallationData}
                    handleChangePumpFields={handleChangePumpFields}
                    isPump={isPump}
                />
            </div>

            {/* Правый насос */}

                {showSecondPump && (
                    <div className={`${styles.pumpSection} ${styles.pumpRight}`}>
                        <PumpEngineFields
                            index={1}
                            availablePumps={availablePumps}
                            availableMaterials={availableMaterials}
                            handleChangeMaterial={handleChangeMaterial}
                            handlePumpSelection={handlePumpSelection}
                            installationData={installationData}
                            setInstallationData={setInstallationData}
                            handleChangePumpFields={handleChangePumpFields}

                        />
                    </div>
                        )}
                    </div>
                    </div>
                    );
                };

// Компонент для полей выбора или ввода насоса и двигателя
const PumpEngineFields = ({ index, availablePumps, handlePumpSelection,setInstallationData, installationData, handleChangePumpFields, isPump,handleChangeMaterial,availableMaterials }) => (
    <div className={styles.horizontalGroup}>
        <div className={styles.formContent}>
            <div>
                <h2 className={styles.formSubtitle}>{isPump ? 'Насос' : `Насос ${index + 1}`}</h2>
                <input className={styles.radioGroup}
                       type="text"
                       list={`pumpList-${index}`}
                       placeholder="Введите или выберите название насоса"
                       value={installationData.pumps[index]?.name || ''}
                       onChange={(e) => handlePumpSelection(index, e.target.value)}/>
                <datalist id={`pumpList-${index}`}>
                    {availablePumps.map(pump => (
                        <option key={pump.id} value={pump.name}>{pump.name}</option>
                    ))}
                </datalist>
                <h3 className={styles.formSubtitle}>Максимальный расход</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите максимальный расход"
                       value={installationData.pumps[index]?.maximumPressure || ""}
                       name="maximumPressure"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Максимальный напор</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите максимальный напор"
                       value={installationData.pumps[index]?.maximumHead || ""}
                       name="maximumHead"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Количество ступеней</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите количество ступеней"
                       value={installationData.pumps[index]?.numberOfSteps || ""}
                       name="numberOfSteps"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>КПД</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите КПД"
                       value={installationData.pumps[index]?.efficiency || ""}
                       name="efficiency"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>NPSH</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите NPSH"
                       value={installationData.pumps[index]?.npsh || ""}
                       name="npsh"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Диаметр входа</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите диаметр входа"
                       value={installationData.pumps[index]?.dm_in || 0}
                       name="dm_in"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Диаметр выхода</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите диаметр выхода"
                       value={installationData.pumps[index]?.dm_out || 0}
                       name="dm_out"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Скорость</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите скорость"
                       value={installationData.pumps[index]?.speed || ""}
                       name="speed"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Длина установки</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите длину установки"
                       value={installationData.pumps[index]?.installationLength || ""}
                       name="installationLength"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Производитель</h3>
                <input className={styles.radioGroup}
                       type="text"
                       placeholder="Введите производителя"
                       value={installationData.pumps[index]?.manufacturer || ""}
                       name="manufacturer"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Описание</h3>
                <input className={styles.radioGroup}
                       type="text"
                       placeholder="Введите описание"
                       value={installationData.pumps[index]?.description || ""}
                       name="description"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Цена</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите цену"
                       value={installationData.pumps[index]?.price || ""}
                       name="price"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>{isPump ? 'Материал' : `Материал ${index + 1}`}</h3>

                <select
                    style={{fontSize: "14px", marginTop: "9px", height: "25px", width: "90%"}}
                    value={installationData.material[index] || ''}
                    onChange={(e) => handleChangeMaterial(index, e.target.value)}
                >
                    <option value="" disabled>Выберите материал</option>
                    {availableMaterials.map((material, idx) => (
                        <option key={idx} value={material}>{material}</option>
                    ))}
                </select>
            </div>
            <EngineData index={index}
                        installationData={installationData}
                        isPump={isPump}
                        setInstallationData={setInstallationData}

            />

        </div>
    </div>

);

export default PumpData;
