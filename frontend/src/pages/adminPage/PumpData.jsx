import React, { useState, useEffect } from 'react';
import {server_url} from "../../config"
import styles from './AdminPage.module.css'
import {useSelector} from "react-redux";
import {useDispatch} from "react-redux";
import EngineData from './EngineData'
import DetailsData from './DetailsData'


const PumpData = ({ installationData, setInstallationData, isPump,details, setDetails,detail, setDetail}) => {
    const [availablePumps, setAvailablePumps] = useState([]);
    const [availableMaterials, setAvailableMaterial] = useState([]);
    const [availableSeries, setAvailableSeries] = useState([]);



    const handleAddLink = (pumpIndex) => {
        const updatedPumps = [...installationData.pumps];
        updatedPumps[pumpIndex].links.push(''); // Добавление новой ссылки
        setInstallationData({ ...installationData, pumps: updatedPumps });
    };


    const handleRemoveLink = (pumpIndex, linkIndex) => {
        // Удаляем ссылку по индексу из конкретного насоса
        const updatedPumps = [...installationData.pumps];
        updatedPumps[pumpIndex].links = updatedPumps[pumpIndex].links.filter((_, i) => i !== linkIndex);
        setInstallationData({ ...installationData, pumps: updatedPumps });
    };

    const handleLinkChange = (e, pumpIndex, linkIndex) => {
        // Обновляем конкретную ссылку на определенном насосе
        const updatedPumps = [...installationData.pumps];
        updatedPumps[pumpIndex].links[linkIndex] = e.target.value;
        setInstallationData({ ...installationData, pumps: updatedPumps });
    };


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
            const series = await fetchSeries();

            if (pumps) setAvailablePumps(pumps);
            if (materials) setAvailableMaterial(materials);
            if (series) setAvailableSeries(series);
        };
        loadOptions();
    }, [installationData.typeInstallations, installationData.subtype]);

    const handleChangePumpFields = (e,index) => {
        const { name, value } = e.target;
        setInstallationData(prevData => {
            const updatedPumps = [...prevData.pumps];
            updatedPumps[index] = {
                ...updatedPumps[index],
                [name]: value
            };
            return {...prevData, pumps: updatedPumps};
        });
    };
    const handleChangeSeries = (e,index) => {
        const { name, value } = e.target;
        setInstallationData(prevData => {
            const updatedSeries = [...prevData.series];
            updatedSeries[index] = value;
            return {...prevData, series: updatedSeries};
        });
    };


    const handlePumpSelection = async (index, value) => {
        const selectedPump = availablePumps.find(pump => pump.name.toLowerCase() === value.toLowerCase());
console.log(installationData);
        if (selectedPump) {
            // Получаем полные данные насоса по ID
            const pumpDetails = await fetchPumpById(selectedPump.id);
            if (pumpDetails) {
                setInstallationData(prevData => {
                    const updatedPumps = [...prevData.pumps];
                    updatedPumps[index] = { ...pumpDetails };
                    const updatedEngines = [...prevData.engines];
                    updatedEngines[index] = {...pumpDetails.engine}
                    return {
                        ...prevData,
                        pumps: updatedPumps,
                        engines: updatedEngines,
                    };
                });
            }
            setDetails(pumpDetails.details)
        } else {
            // Ввод вручную, сохраняем как текст
            setInstallationData(prevData => {
                const updatedPumps = [...prevData.pumps];
                updatedPumps[index] = {
                    ...updatedPumps[index],
                    name: value
                };
                return {
                    ...prevData,
                    pumps: updatedPumps
                };
            });
        }
    };
    const fetchSeries = async () => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            const response = await fetch(`${server_url}/api/simple/admin/series`, {
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
            console.log(response.body)
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
                    handleChangeSeries={handleChangeSeries}
                    handleAddLink={handleAddLink}
                    handleRemoveLink={handleRemoveLink}
                    handleLinkChange={handleLinkChange}
                    index={0}
                    availablePumps={availablePumps}
                    availableMaterials={availableMaterials}
                    handleChangeMaterial={handleChangeMaterial}
                    handlePumpSelection={handlePumpSelection}
                    installationData={installationData}
                    setInstallationData={setInstallationData}
                    handleChangePumpFields={handleChangePumpFields}
                    isPump={isPump}
                    availableSeries={availableSeries}
                    detail={detail}
                    setDetail={setDetail}
                    details={details}
                    setDetails={setDetails}
                />
            </div>

            {/* Правый насос */}

                {showSecondPump && (
                    <div className={`${styles.pumpSection} ${styles.pumpRight}`}>
                        <PumpEngineFields
                            handleChangeSeries={handleChangeSeries}
                            handleAddLink={handleAddLink}
                            handleRemoveLink={handleRemoveLink}
                            handleLinkChange={handleLinkChange}
                            index={1}
                            availablePumps={availablePumps}
                            availableMaterials={availableMaterials}
                            handleChangeMaterial={handleChangeMaterial}
                            handlePumpSelection={handlePumpSelection}
                            installationData={installationData}
                            setInstallationData={setInstallationData}
                            handleChangePumpFields={handleChangePumpFields}
                            availableSeries={availableSeries}
                            detail={detail}
                            setDetail={setDetail}
                            details={details}
                            setDetails={setDetails}
                        />
                    </div>
                        )}
                    </div>
                    </div>
                    );
                };

// Компонент для полей выбора или ввода насоса и двигателя
const PumpEngineFields = ({handleChangeSeries,handleAddLink,handleRemoveLink,handleLinkChange, index, availablePumps, handlePumpSelection,setInstallationData, installationData, handleChangePumpFields, isPump,handleChangeMaterial,availableMaterials,availableSeries,detail, setDetail,details, setDetails}) => (
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
                <h3 className={styles.formSubtitle}>{isPump ? 'Серия' : `Серия ${index + 1}`}</h3>
                <select
                    style={{fontSize: "14px", marginTop: "9px", height: "25px"}}
                    value={installationData.series[index] || ""}
                    name="series"
                    onChange={(e) => handleChangeSeries(e, index)}
                >
                    <option value="" disabled>Выберите серию</option>
                    {availableSeries.map((series, idx) => (
                        <option key={idx} value={series.name}>{series.name}</option>
                    ))}
                </select>
                <h3 className={styles.formSubtitle}>Наличие</h3>
                <select
                    style={{fontSize: "14px", marginTop: "9px", height: "25px"}}
                    value={installationData.pumps[index].availability || false}
                    name="availability"
                    onChange={(e) => handleChangePumpFields(e, index)}
                >
                    <option value="" disabled>Выберите наличие</option>
                    <option value="true">В наличе</option>
                    <option value="false">Отсутствует</option>
                </select>

                <h3 className={styles.formSubtitle}>Максимальный расход</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите максимальный расход"
                       value={installationData.pumps[index]?.maximumPressure || 0}
                       name="maximumPressure"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Максимальный напор</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите максимальный напор"
                       value={installationData.pumps[index]?.maximumHead || 0}
                       name="maximumHead"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Количество ступеней</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите количество ступеней"
                       value={installationData.pumps[index]?.numberOfSteps || 0}
                       name="numberOfSteps"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>КПД</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите КПД"
                       value={installationData.pumps[index]?.efficiency || 0}
                       name="efficiency"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>NPSH</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите NPSH"
                       value={installationData.pumps[index]?.npsh || 0}
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
                       value={installationData.pumps[index]?.speed || 0}
                       name="speed"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Температуру жидкости</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите длину температуру жидкости"
                       value={installationData.pumps[index]?.liquidTemperature || 0}
                       name="liquidTemperature"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Температура среды(Макс)</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите длину мак температуру внешней среды"
                       value={installationData.pumps[index]?.ambientTemperatureMax || 0}
                       name="ambientTemperatureMax"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Температура среды(Мин)</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите длину мин температуру внешней среды"
                       value={installationData.pumps[index]?.ambientTemperatureMin || 0}
                       name="ambientTemperatureMin"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Стандарт подключения</h3>
                <input className={styles.radioGroup}
                       type="text"
                       placeholder="Введите стандарт подключения"
                       value={installationData.pumps[index]?.connectionStandard || ""}
                       name="connectionStandard"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Гидравлический выбор</h3>
                <input className={styles.radioGroup}
                       type="text"
                       placeholder="Введите гидравлический выбор"
                       value={installationData.pumps[index]?.hydraulicSelection || ""}
                       name="hydraulicSelection"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Вес</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите вес"
                       value={installationData.pumps[index]?.weight || 0}
                       name="weight"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>Длина установки</h3>
                <input className={styles.radioGroup}
                       type="number"
                       placeholder="Введите длину установки"
                       value={installationData.pumps[index]?.installationLength || 0}
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
                       value={installationData.pumps[index]?.price || 0}
                       name="price"
                       onChange={(e) => handleChangePumpFields(e, index)}
                />
                <h3 className={styles.formSubtitle}>{isPump ? 'Материал' : `Материал ${index + 1}`}</h3>

                <select
                    style={{fontSize: "14px", marginTop: "9px", height: "25px"}}
                    value={installationData.material[index] || ''}
                    onChange={(e) => handleChangeMaterial(index, e.target.value)}
                >
                    <option value="" disabled>Выберите материал</option>
                    {availableMaterials.map((material, idx) => (
                        <option key={idx} value={material}>{material}</option>
                    ))}
                </select>

                <DetailsData detail={detail}
                             setDetail={setDetail}
                             details={details}
                             setDetails={setDetails}/>
                <div>
                    <h3 className={styles.formSubtitle}>Ссылки</h3>
                    {(installationData.pumps[index].links || []).map((linka, linkIndex) => (
                        <div key={linkIndex} className={styles.radioGroup}>
                            <input
                                className={{marginLeft: "-10em"}}
                                type="text"
                                placeholder="Введите ссылку"
                                value={linka}
                                onChange={(e) => handleLinkChange(e, index, linkIndex)}
                            />
                            <button
                                type="button"
                                onClick={() => handleRemoveLink(index, linkIndex)}
                            >
                                Удалить ссылку
                            </button>
                        </div>
                    ))}
                    <br/>
                    <button
                        type="button"
                        onClick={() => handleAddLink(index)}
                    >

                        Добавить ссылку
                    </button>
                </div>


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
