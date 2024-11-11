import React, { useState, useEffect } from 'react';
import {server_url} from "../../config"
import styles from './AdminPage.module.css'
import {useSelector} from "react-redux";

const PumpData = ({ installationData, setInstallationData}) => {
    const [availablePumps, setAvailablePumps] = useState([]);
    const [availableEngines, setAvailableEngines] = useState([]);
    const [availableMaterials, setAvailableMaterial] = useState([]);

    const token = useSelector((state) => state.user.token);


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

            const engines = await fetchAllEngines();
            const materials = await fetchAllMaterial();

            if (pumps) setAvailablePumps(pumps);
            if (engines) setAvailableEngines(engines);
            if (materials) setAvailableMaterial(materials);
        };
        loadOptions();
    }, [installationData.typeInstallations, installationData.subtype]);

    const fetchAllMaterial = async () => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/materials`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
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
    const handleChangeEngineFields = (e,index) => {
        const { name, value } = e.target;
        setInstallationData(prevData => {
            const updatedEngine = [...prevData.engines];
            updatedEngine[index] = {
                ...updatedEngine[index],
                [name]: value,
                name: ''
            };
            console.log(installationData.engines[index].powerType)
            return {...prevData, engines: updatedEngine};
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
                    const updatedEngineIds = [...prevData.engineIds];
                    updatedEngineIds[index] = engineDetails.id;
                    return {
                        ...prevData,
                        engineIds: updatedEngineIds,
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
                const updatedEngineIds = [...prevData.engineIds];
                updatedEngineIds[index] = "";
                return {
                    ...prevData,
                    pumps: updatedPumps,
                    engines: updatedEngines,
                    engineIds: updatedEngineIds,
                };
            });
        }
    };

    const fetchAllPumps = async () => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/pumps`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
            console.log(response)
            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error("Не удалось получить список насосов:", error);
            return null;
        }
    };
    const fetchAllEngines = async () => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/engines`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });

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
    const fetchPumpById = async (id) => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/pumps/${id}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });
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
    const fetchEngineById = async (id) => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/engines/${id}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });

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
    const fetchInstallationPumps = async (type, subType) => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/instPump?type=${type}&subType=${subType}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            });

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
    const showSecondPump = installationData.subtype === "AFEIJP";

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
                    availableEngines={availableEngines}
                    handlePumpSelection={handlePumpSelection}
                    handleEngineSelection={handleEngineSelection}
                    installationData={installationData}
                    setInstallationData={setInstallationData}
                    handleChangePumpFields={handleChangePumpFields}
                    handleChangeEngineFields={handleChangeEngineFields}
                    availableMaterials={availableMaterials}
                    handleChangeMaterial={handleChangeMaterial}
                />
            </div>

            {/* Правый насос */}

                {showSecondPump && (
                    <div className={`${styles.pumpSection} ${styles.pumpRight}`}>
                        <PumpEngineFields
                            index={1}
                            availablePumps={availablePumps}
                            availableEngines={availableEngines}
                            handlePumpSelection={handlePumpSelection}
                            handleEngineSelection={handleEngineSelection}
                            installationData={installationData}
                            setInstallationData={setInstallationData}
                            handleChangePumpFields={handleChangePumpFields}
                            handleChangeEngineFields={handleChangeEngineFields}
                            availableMaterials={availableMaterials}
                            handleChangeMaterial={handleChangeMaterial}
                        />
                    </div>
                        )}
                    </div>
                    </div>
                    );
                };

// Компонент для полей выбора или ввода насоса и двигателя
const PumpEngineFields = ({ index, availablePumps, availableEngines,availableMaterials, handleChangeMaterial, handlePumpSelection, handleEngineSelection, installationData,handleChangePumpFields,handleChangeEngineFields }) => (
    <div className={styles.formContent}>
        <h2 className={styles.formSubtitle}>Насос {index + 1}</h2>
        <input className={styles.radioGroup}
               type="text"
               list={`pumpList-${index}`}
               placeholder="Введите или выберите имя насоса"
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
               placeholder="Введите расход"
               value={installationData.pumps[index]?.maximumPressure || ""}
               name="maximumPressure"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>Максимальный напор</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите напор"
               value={installationData.pumps[index]?.maximumHead || ""}
               name="maximumHead"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>Количество ступений</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите кол-во ступений"
               value={installationData.pumps[index]?.numberOfSteps || ""}
               name="numberOfSteps"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>КПД</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите кпд"
               value={installationData.pumps[index]?.efficiency || ""}
               name="efficiency"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>npsh</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите npsh"
               value={installationData.pumps[index]?.npsh || ""}
               name="npsh"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>dmIn</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите dmIn"
               value={installationData.pumps[index].dm_in !== null && installationData.pumps[index].dm_in !== undefined ? installationData.pumps[index].dm_in : ''}
               name="dm_in"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>dmOut</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите dmOut"
               value={installationData.pumps[index].dm_out !== null && installationData.pumps[index].dm_out !== undefined ? installationData.pumps[index].dm_out : ''}
               name="dm_out"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>speed</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите speed"
               value={installationData.pumps[index]?.speed || ""}
               name="speed"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>installationLength</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите installationLength"
               value={installationData.pumps[index]?.installationLength || ""}
               name="installationLength"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>manufacturer</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите manufacturer"
               value={installationData.pumps[index]?.manufacturer || ""}
               name="manufacturer"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>description</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите description"
               value={installationData.pumps[index]?.description || ""}
               name="description"
               onChange={(e) => handleChangePumpFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>price</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите price"
               value={installationData.pumps[index]?.price || ""}
               name="price"
               onChange={(e) => handleChangePumpFields(e, index)}
        />

        <h2 className={styles.formSubtitle}>Двигатель {index + 1}</h2>
        <input className={styles.radioGroup}
               type="text"
               list={`enginesList-${index}`}
               placeholder="Введите или выберите имя насоса"
               value={installationData.engines[index]?.name || ''}
               onChange={(e) => handleEngineSelection(index, e.target.value)}/>
        <datalist id={`enginesList-${index}`}>
            {availableEngines.map(engine => (
                <option key={engine.id} value={engine.name}>{engine.name}</option>
            ))}
        </datalist>
        <h3 className={styles.formSubtitle}>Тип насоса</h3>
        <div className={styles.selectWrapper}>
            <select value={installationData.engines[index]?.pumpType || ''} name="pumpType"
                    onChange={(e) => handleChangeEngineFields(e, index)}>
                <option value="">Выберите тип насоса</option>
                <option value="VERTICAL_MULTISTAGE">Вертикальный мульти</option>
                <option value="IN_LINE">Ин-Лайн</option>
            </select>

        </div>
        <h3 className={styles.formSubtitle}>power</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите power"
               value={installationData.engines[index]?.power || ""}
               name="power"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>amperage</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите amperage"
               value={installationData.engines[index]?.amperage || ""}
               name="amperage"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>voltage</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите voltage"
               value={installationData.engines[index]?.voltage || ""}
               name="voltage"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>turnovers</h3>
        <input className={styles.radioGroup}
               type="number"
               placeholder="Введите turnovers"
               value={installationData.engines[index]?.turnovers || ""}
               name="turnovers"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>manufacturer</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите manufacturer"
               value={installationData.engines[index]?.manufacturer || ""}
               name="manufacturer"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>execution</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите execution"
               value={installationData.engines[index]?.execution || ""}
               name="execution"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>typeOfProtection</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите typeOfProtection"
               value={installationData.engines[index]?.typeOfProtection || ""}
               name="typeOfProtection"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>insulationClass</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите insulationClass"
               value={installationData.engines[index]?.insulationClass || ""}
               name="insulationClass"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>color</h3>
        <input className={styles.radioGroup}
               type="text"
               placeholder="Введите color"
               value={installationData.engines[index]?.color || ""}
               name="color"
               onChange={(e) => handleChangeEngineFields(e, index)}
        />
        <h3 className={styles.formSubtitle}>Конфигурация материалов {index + 1}</h3>
        <div className={styles.selectWrapper}>
            <select
                className={styles.radioGroup}
                value={installationData.material[index] || ''}
                onChange={(e) => handleChangeMaterial(index, e.target.value)}
            >
                <option value="" disabled>Выберите материал</option>
                {availableMaterials.map((material, idx) => (
                    <option key={idx} value={material}>{material}</option>
                ))}
            </select>
        </div>
    </div>
        );

        export default PumpData;