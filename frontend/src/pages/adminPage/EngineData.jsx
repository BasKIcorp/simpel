import React, {useEffect, useState} from "react";
import styles from './AdminPage.module.css'
import {server_url} from "../../config"
import {useDispatch} from "react-redux";


const EngineData = ({ index, installationData, isPump,setInstallationData }) => {
    const dispatch = useDispatch();

    const [availableEngines, setAvailableEngines] = useState([]);

    useEffect(() => {
        const loadOptions = async () => {
            const engines = await fetchAllEngines();


            if (engines) setAvailableEngines(engines);

        };
        loadOptions();
    }, [installationData.typeInstallations, installationData.subtype]);

    const handleChangeEngineFields = (e,index) => {
        const { name, value } = e.target;
        setInstallationData(prevData => {
            const updatedEngine = [...prevData.engines];
            updatedEngine[index] = {
                ...updatedEngine[index],
                [name]: value
            };
            console.log(installationData.engines[index].powerType)
            return {...prevData, engines: updatedEngine};
        });
    };

    const handleEngineSelection = async (index, value) => {
        const selectedEngine = availableEngines.find(engine => engine.name.toLowerCase() === value.toLowerCase());

        if (selectedEngine) {
            // Получаем полные данные насоса по ID
            const engineDetails = await fetchEngineById(selectedEngine.id);

            if (engineDetails) {
                setInstallationData(prevData => {
                    const updatedPumps = [...prevData.pumps];
                    updatedPumps[index] = { ...updatedPumps[index], name: ''};
                    const updatedEngines = [...prevData.engines];
                    updatedEngines[index] = {...engineDetails};
                    return {
                        ...prevData,
                        pumps: updatedPumps,
                        engines: updatedEngines
                    };
                });
            }
        } else {
            // Ввод вручную, сохраняем как текст
            setInstallationData(prevData => {
                const updatedPumps = [...prevData.pumps];
                updatedPumps[index] = { ...updatedPumps[index], name: ''};
                const updatedEngines = [...prevData.engines];
                updatedEngines[index] = {...updatedEngines[index],
                    name: value};
                return {
                    ...prevData,
                    pumps: updatedPumps,
                    engines: updatedEngines
                };
            });
        }
    };


    const fetchEngineById = async (id) => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            const response = await fetch(`${server_url}/api/simple/admin/engines/${id}`, {
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
            console.error("Не удалось получить данные двигателя:", error);
            return null;
        }
    };

    const fetchAllEngines = async () => {
        try {
            const userData = localStorage.getItem("token");
            const token = JSON.parse(userData).token
            const response = await fetch(`${server_url}/api/simple/admin/engines`, {
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

            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Не удалось получить список двигателей:", error);
            return null;
        }
    };



        return (
            <div className={styles.engine}>
                    <h2 className={styles.formSubtitle}>{isPump ? 'Двигатель' : `Двигатель ${index + 1}`}</h2>
                    <input className={styles.radioGroup}
                           type="text"
                           list={`enginesList-${index}`}
                           placeholder="Введите или выберите название двигателя"
                           value={installationData.engines[index]?.name || ''}
                           onChange={(e) => handleEngineSelection(index, e.target.value)}/>
                    <datalist id={`enginesList-${index}`}>
                            {availableEngines.map(engine => (
                                <option key={engine.id} value={engine.name}>{engine.name}</option>
                            ))}
                    </datalist>
                    <h3 className={styles.formSubtitle}>Тип насоса</h3>
                    <select style={{fontSize: "14px", marginTop: "9px", height: "25px", width: "90%"}}
                            value={installationData.engines[index]?.pumpType || ''} name="pumpType"
                            onChange={(e) => handleChangeEngineFields(e, index)}>

                            <option value="">Выберите тип насоса</option>
                            <option value="VERTICAL_MULTISTAGE">Вертикальный мульти</option>
                            <option value="IN_LINE">Инлайн</option>
                    </select>
                    <h3 className={styles.formSubtitle}>Мощность</h3>
                    <input className={styles.radioGroup}
                           type="number"
                           placeholder="Введите мощность"
                           value={installationData.engines[index]?.power || ""}
                           name="power"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Ампераж</h3>
                    <input className={styles.radioGroup}
                           type="number"
                           placeholder="Введите ампераж"
                           value={installationData.engines[index]?.amperage || ""}
                           name="amperage"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Напряжение</h3>
                    <input className={styles.radioGroup}
                           type="number"
                           placeholder="Введите напряжение"
                           value={installationData.engines[index]?.voltage || ""}
                           name="voltage"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Обороты</h3>
                    <input className={styles.radioGroup}
                           type="number"
                           placeholder="Введите обороты"
                           value={installationData.engines[index]?.turnovers || ""}
                           name="turnovers"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Производитель</h3>
                    <input className={styles.radioGroup}
                           type="text"
                           placeholder="Введите производителя"
                           value={installationData.engines[index]?.manufacturer || ""}
                           name="manufacturer"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Исполнение</h3>
                    <input className={styles.radioGroup}
                           type="text"
                           placeholder="Введите исполнение"
                           value={installationData.engines[index]?.execution || ""}
                           name="execution"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Тип защиты</h3>
                    <input className={styles.radioGroup}
                           type="text"
                           placeholder="Введите тип защиты"
                           value={installationData.engines[index]?.typeOfProtection || ""}
                           name="typeOfProtection"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Класс изоляции</h3>
                    <input className={styles.radioGroup}
                           type="text"
                           placeholder="Введите класс изоляции"
                           value={installationData.engines[index]?.insulationClass || ""}
                           name="insulationClass"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Цвет</h3>
                    <input className={styles.radioGroup}
                           type="text"
                           placeholder="Введите цвет"
                           value={installationData.engines[index]?.color || ""}
                           name="color"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />
                    <h3 className={styles.formSubtitle}>Цена</h3>
                    <input className={styles.radioGroup}
                           type="number"
                           placeholder="Введите цену"
                           value={installationData.engines[index]?.price || ""}
                           name="price"
                           onChange={(e) => handleChangeEngineFields(e, index)}
                    />

            </div>
        );

}
export default EngineData;