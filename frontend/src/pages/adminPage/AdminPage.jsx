// InstallationForm.js - основной компонент для выбора установки
import React, { useState } from "react";
import {useDispatch, useSelector} from "react-redux";
import { setGeneralInfo } from "../../store/pumpSlice";
// import PumpDataForm from "./PumpDataForm";
// import PhotoAndPointsForm from "./PhotoAndPointsForm";
import styles from "./AdminPage.module.css";
import {Header} from "../../components/UI/Header";
import {GMFields} from './GMFields'
import {HozPitPns} from './HozPitPns'
import PumpData from "./PumpData";
import PointsData from "./PointsData";
import {server_url} from "../../config";
import {useNavigate} from "react-router-dom";
import { useEffect } from "react";
import { Grid, Collapse,Box } from '@mui/material';
import EngineData from './EngineData';


export const AdminPage = () => {
    const dispatch = useDispatch();
    const [installationData, setInstallationData] = useState({
        typeInstallations: '',
        subtype: '',
        countMainPumps: 1,
        countSparePumps: 0,
        coolantType: '',
        temperature: '',
        powerType: '',
        controlType: '',
        concentration: 0,
        pumpTypeForSomeInstallation: '',
        pumpIds: ["",""],
        engineIds: ["",""],
        price: 0,
        pumps: [{
            name: '',
            manufacturer: '',
            speed: 0,
            numberOfSteps: 0,
            maximumPressure: 0, // flowrated
            maximumHead: 0,
            article: '',
            efficiency: 0,
            npsh: 0,
            dm_in: 0,
            dm_out: 0,
            installationLength: 0,
            description: '',
            price: 0

        },
            {
                name: '',
                manufacturer: '',
                speed: 0,
                numberOfSteps: 0,
                maximumPressure: 0, // flowrated
                maximumHead: 0,
                article: '',
                efficiency: 0,
                npsh: 0,
                dm_in: 0,
                dm_out: 0,
                installationLength: 0,
                description: '',
                price: 0

            }],
        engines: [{
            pumpType: '',
            manufacturer: '',
            execution: '',
            power: 0,
            amperage: 0,
            voltage: 0,
            turnovers: 0,
            typeOfProtection: '',
            insulationClass: '',
            color: '',
            price: 0
        },
            {
                pumpType: '',
                manufacturer: '',
                execution: '',
                power: 0,
                amperage: 0,
                voltage: 0,
                turnovers: 0,
                typeOfProtection: '',
                insulationClass: '',
                color: '',
                price: 0
            }],
        material: ['','']
    });
    const [files, setFiles] = useState([]);
    const [points,setPoints]=useState([]);
    const token = useSelector((state) => state.user.token);

    const [visibleInst, setVisibleInst] = useState(false);
    const [visiblePump, setVisiblePump] = useState(false);
    const [visibleEngine, setVisibleEngine] = useState(false);

    const toggleExpandInst = () => {
        setVisibleInst(!visibleInst);
    };
    const toggleExpandPump = () => {
        setVisiblePump(!visiblePump);
    };
    const toggleExpandEngine = () => {
        setVisibleEngine(!visibleEngine);
    };

    useEffect(() => {
        setInstallationData(prevData => {
            let newEngines = [...prevData.engines];
            let newPumps = [...prevData.pumps]

            // Проверяем, выбран ли подтип "AFEIJP"
            if (installationData.subtype === "AFEIJP") {
                // Если выбран "AFEIJP", оставляем только один двигатель
                 // Вставляем только первый двигатель
                newEngines = [newEngines[0], newEngines[0]];
                newPumps = [newPumps[0],newPumps[0]];
            } else {
                newEngines = [newEngines[0]];
                newPumps = [newPumps[0]];
                }


            return {
                ...prevData,
                engines: newEngines, // Обновляем массив двигателей'
                pumps: newPumps
            };
        });
    }, [installationData.subtype]);

    const handleClickAddPump = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append("pump", new Blob([JSON.stringify(installationData.pumps[0])], { type: "application/json" }));
        formData.append("engine", new Blob([JSON.stringify(installationData.engines[0])], { type: "application/json" }));
        formData.append("engineId", new Blob([JSON.stringify(installationData.engineIds[0])], { type: "application/json" }));
        files.forEach(file => {
            formData.append('files', file);
        });

        formData.append("points", new Blob([JSON.stringify(points)], { type: "application/json" }));
        try {
            const response = await fetch(`${server_url}/api/simple/admin/save/pump`, {
                headers: {
                    Authorization: `Bearer ${token}`
                },
                method: "POST",
                body: formData,
            });
            console.log(response.json())
            if (response.ok) {
                alert("Данные успешно сохранены!");
                window.location.reload()
            } else {
                if (!response) {
                    dispatch({ type: 'remove_user' });
                    console.log('Нет ответа от сервера, токен удалён');
                    return null;
                }

                // Проверяем, если статус 401 или 403 (неавторизован или нет прав)
                if (response.status === 401 || response.status === 403) {
                    dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора

                    if (response.status === 401) {
                        console.log('Испорченный токен');
                    } else {
                        console.log('Нет прав для выполнения операции');
                    }

                    return null; // Завершаем выполнение
                }
                const data = await response.json();
                alert(`Ошибка: ${data.message}`);
            }

        } catch (error) {
            console.error(error);
            alert("Произошла ошибка при отправке данных.");
        }
    }

    const handleClickAddEngine = async (e) => {
        e.preventDefault();


        try {
            const response = await fetch(`${server_url}/api/simple/admin/save/engine`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                method: "POST",
                body: JSON.stringify(installationData.engines[0])
            });
            if (response.ok) {
                alert("Данные успешно сохранены!");
                window.location.reload()
            } else {
                if (!response) {
                    dispatch({ type: 'remove_user' });
                    console.log('Нет ответа от сервера, токен удалён');
                    return null;
                }

                // Проверяем, если статус 401 или 403 (неавторизован или нет прав)
                if (response.status === 401 || response.status === 403) {
                    dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора

                    if (response.status === 401) {
                        console.log('Испорченный токен');
                    } else {
                        console.log('Нет прав для выполнения операции');
                    }

                    return null; // Завершаем выполнение
                }
                const data = await response.json();
                alert(`Ошибка: ${data.message}`);
            }

        } catch (error) {
            console.error(error);
            alert("Произошла ошибка при отправке данных.");
        }
    }

    const handleInstallationTypeChange = (event) => {
        const value = event.target.value;
        console.log(value);
        setInstallationData(prevData => ({
            ...prevData,
            typeInstallations: value,
            subtype: ""
        }));

    };


    const handleInstallationSubtypeChange = (event) => {
        const value = event.target.value;
        setInstallationData(prevData => ({
            ...prevData,
            subtype: value
        }));

    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInstallationData({
            ...installationData,
            [name]: value
        });
        console.log(installationData)
    };
    const handleFileChange = (e) => {
        const selectedFiles = Array.from(e.target.files).slice(0, 3); // Ограничиваем до 3 фото

        // Обновляем состояние, добавляя новые выбранные файлы к уже существующим
        setFiles((prevFiles) => {
            // Соединяем предыдущие файлы с новыми, не превышая лимит в 3 файла
            const updatedFiles = [...prevFiles, ...selectedFiles].slice(0, 3);
            return updatedFiles;
        });
    };
    const handleRemoveFile = (index) => {
        setFiles(files.filter((_, i) => i !== index));
    };

    const isFormValid = () => {
        // Пример проверки обязательных полей
        return installationData.typeInstallations !== '' &&
            installationData.subtype !== '' &&
            installationData.countMainPumps > 0 &&
            installationData.countSparePumps >= 0 &&
            installationData.powerType !== '' &&
            installationData.controlType !== '' &&
            files.length > 0;  // Например, проверка на наличие файлов
    };
    const handleClick = async () => {
        try {
            const response = await fetch(`${server_url}/api/simple/admin/users`, {
                headers: {
                    Authorization: `Bearer ${token}`
                },
                method: "GET",
            });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append("request", new Blob([JSON.stringify(installationData)], { type: "application/json" }));

        files.forEach(file => {
            formData.append('files', file);
        });

        formData.append("points", new Blob([JSON.stringify(points)], { type: "application/json" }));
        try {
            const response = await fetch(`${server_url}/api/simple/admin/save`, {
                headers: {
                    Authorization: `Bearer ${token}`
                },
                method: "POST",
                body: formData,
            });
            console.log(response.json())
            if (response.ok) {
                alert("Данные успешно сохранены!");
                window.location.reload()
            } else {
                if (!response) {
                    dispatch({ type: 'remove_user' });
                    console.log('Нет ответа от сервера, токен удалён');
                    return null;
                }

                // Проверяем, если статус 401 или 403 (неавторизован или нет прав)
                if (response.status === 401 || response.status === 403) {
                    dispatch({ type: 'remove_user' }); // Удаляем пользователя из стора

                    if (response.status === 401) {
                        console.log('Испорченный токен');
                    } else {
                        console.log('Нет прав для выполнения операции');
                    }

                    return null; // Завершаем выполнение
                }
                const data = await response.json();
                alert(`Ошибка: ${data.message}`);
            }

        } catch (error) {
            console.error(error);
            alert("Произошла ошибка при отправке данных.");
        }
    };
    const navigate = useNavigate();
    return (

        <div>

            <Header/>


            <div className={styles.wrapper}>

                <div className={styles.rectangle}>

                    <button className={styles.buttonMain} onClick={toggleExpandInst}>
                        <div className={styles.textButton}>
                            Добавить Установку
                        </div>
                        <br/>
                    </button>
                    <button className={styles.buttonMain} onClick={toggleExpandPump}>
                        <br/>
                        Добавить Насос
                        <br/>
                        <br/>
                    </button>
                    <button className={styles.buttonMain} onClick={toggleExpandEngine}>
                        <div className={styles.textButton}>
                            Добавить Движок
                        </div>
                            <br/>
                    </button>
                    <button
                        className={styles.buttonMain}
                        onClick={() => navigate('/selection/installation_choice')}
                    >
                        Перейти к выбору установки
                    </button>
                    <button className={styles.buttonMain} onClick={handleClick}>Отправить на почту пользователей
                    </button>


                    <Collapse  in={visibleInst}>

                        <div style={{marginTop: "10%"}}className={styles.contentContainer}>
                            <div className={styles.selectWrapper}>

                                <h2 className={styles.formSubtitle}>Тип установки</h2>

                                <select className={styles.select} onChange={handleInstallationTypeChange}>
                                    <option value="">Выберите тип установки</option>
                                    <option title="Установка для циркуляционных систем" value="GM">Выносной гидромодуль
                                    </option>
                                    <option value="HOZPIT">Установка повышения давления</option>
                                    <option value="PNS">Установка системы пожаротушения</option>
                                </select>
                            </div>
                        </div>
                        <div>

                            {installationData.typeInstallations === "GM" && (
                                <div className={styles.selectWrapper}>
                                    <h2 className={styles.formSubtitle}>Тип гидромодуля</h2>
                                    <div className={styles.radioGroup}>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="FREQUENCY_CONTROLLED"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            С частотным управлением по давлению на выходе
                                        </label>
                                        <br/>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="FREQUENCY_CONTROLLED"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            С частотным управлением по перепаду давления
                                        </label>
                                        <br/>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="RELAY_CONTROL"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            С релейным управлением по давлению на выходе
                                        </label>
                                        <br/>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="4"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            С релейным управлением по перепаду давления
                                        </label>
                                        <br/>
                                    </div>
                                </div>
                            )}

                            {installationData.typeInstallations === "HOZPIT" && (
                                <div className={styles.selectWrapper}>
                                    <h2 className={styles.formSubtitle}>Тип хоз-пит</h2>
                                    <div className={styles.radioGroup}>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="CASCADE_FREQUENCY_CONTROL"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            С мульти-частотным регулированием (ПЧ на каждый насос)
                                        </label>
                                        <br/>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="RELAY_CONTROL"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            С релейным управлением
                                        </label>
                                    </div>
                                </div>
                            )}

                            {installationData.typeInstallations === "PNS" && (
                                <div className={styles.selectWrapper}>
                                    <h2 className={styles.formSubtitle}>Тип ПНС</h2>
                                    <div className={styles.radioGroup}>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="ERW_SYSTEM"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            Установка для системы ВПВ
                                        </label>
                                        <br/>
                                        <label>
                                            <input
                                                type="radio"
                                                name="subtype"
                                                value="AFEIJP"
                                                onChange={handleInstallationSubtypeChange}
                                            />{" "}
                                            Установка автоматического пожаротушения с жокей-насосом (АУПТ)
                                        </label>
                                    </div>
                                </div>
                            )}
                        </div>
                        <div className={styles.horizontalGroup}>
                            <div className={styles.selectWrapper}>
                                <h3 className={styles.formSubtitle}>Количество насосов</h3>
                                <div className={styles.radioGroup}>
                                    <h4>Рабочих</h4>
                                    <label>
                                        <input type="radio" name="countMainPumps" value="1" defaultChecked={true}
                                               onChange={handleChange}/> 1
                                    </label>
                                    <label>
                                        <input type="radio" name="countMainPumps" value="2"
                                               onChange={handleChange}/> 2
                                    </label>
                                    <label>
                                        <input type="radio" name="countMainPumps" value="3"
                                               onChange={handleChange}/> 3
                                    </label>
                                    <label>
                                        <input type="radio" name="countMainPumps" value="4"
                                               onChange={handleChange}/> 4
                                    </label>
                                </div>
                                <div className={styles.radioGroup}>
                                    <h4>Резервных</h4>
                                    <label>
                                        <input type="radio" name="countSparePumps" value="0" defaultChecked={true}
                                               onChange={handleChange}/> 0
                                    </label>
                                    <label>
                                        <input type="radio" name="countSparePumps" value="1"
                                               onChange={handleChange}/> 1
                                    </label>
                                    <label>
                                        <input type="radio" name="countSparePumps" value="2"
                                               onChange={handleChange}/> 2
                                    </label>
                                </div>
                                <h3 className={styles.formSubtitle}>Цена</h3>
                                <input className={styles.radioGroup}
                                       type="number"
                                       placeholder="Введите цену"
                                       value={installationData?.price || ""}
                                       name="price"
                                       onChange={handleChange}
                                />
                            </div>
                            <div className={styles.selectWrapper}>
                                <div className={styles.rightColumn}>
                                    <h3 className={styles.formSubtitle}>Тип подключения</h3>
                                    <select className={styles.select} name='powerType' onChange={handleChange}>
                                        <option value="">Выберите тип подключения</option>
                                        <option value="З80">З80</option>
                                        <option value="З">З</option>
                                    </select>


                                    <h3 className={styles.formSubtitle}>Тип управления</h3>
                                    <select className={styles.select} name='controlType' onChange={handleChange}>
                                        <option value="">Выберите тип управления</option>
                                        <option value="asd">asd</option>
                                        <option value="qwe">qwe</option>
                                    </select>
                                </div>

                            </div>

                        </div>


                        {installationData.typeInstallations === "GM" && (
                            <div><GMFields installationData={installationData}
                                           setInstallationData={setInstallationData}/>
                            </div>)}
                        {installationData.typeInstallations === "HOZPIT" && (
                            <div><HozPitPns installationData={installationData}
                                            setInstallationData={setInstallationData}/>
                            </div>)}
                        {installationData.typeInstallations === "PNS" && (
                            <div><HozPitPns installationData={installationData}
                                            setInstallationData={setInstallationData}/>
                            </div>)}
                        <div><PumpData installationData={installationData} setInstallationData={setInstallationData}
                                       isPump={false}/>
                        </div>
                        <div className={styles.selectWrapper}>
                            <h3>Добавить до трех фото:</h3>
                            <input
                                type="file"
                                accept="image/*"
                                multiple
                                style={{display: 'none'}}
                                id="fileInput"
                                onChange={handleFileChange}
                            />
                            <button className={styles.button} type="button"
                                    onClick={() => document.getElementById('fileInput').click()}>
                                Выбрать фото
                            </button>
                            {files.length > 0 && (
                                <div>
                                    <h3>Предпросмотр фото:</h3>
                                    {files.map((file, index) => (
                                        <div key={index} style={{marginBottom: '10px'}}>
                                            <img
                                                src={URL.createObjectURL(file)}
                                                alt="Preview"
                                                width="25%"
                                                style={{marginRight: '10px'}}
                                            />
                                            <button className={styles.button} type="button"
                                                    onClick={() => handleRemoveFile(index)}>Удалить фото
                                            </button>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                        <div><PointsData points={points} setPoints={setPoints}/></div>
                        <button style={{transform: "translateX(7%)"}}
                            className={styles.button}
                            type="button"
                            onClick={handleSubmit}
                            disabled={!isFormValid()}
                        >
                            Сохранить
                        </button>

                    </Collapse>
                    <Collapse in={visiblePump}>

                        <div style={{marginTop: "10%"}}><PumpData installationData={installationData}
                                                                  setInstallationData={setInstallationData}
                                                                  isPump={true}/></div>
                        <div className={styles.selectWrapper}>
                            <h3>Добавить до трех фото:</h3>
                            <input
                                type="file"
                                accept="image/*"
                                multiple
                                style={{display: 'none'}}
                                id="fileInput"
                                onChange={handleFileChange}
                            />
                            <button className={styles.button} type="button"
                                    onClick={() => document.getElementById('fileInput').click()}>
                                Выбрать фото
                            </button>
                            {files.length > 0 && (
                                <div>
                                    <h3>Предпросмотр фото:</h3>
                                    {files.map((file, index) => (
                                        <div key={index} style={{marginBottom: '10px'}}>
                                            <img
                                                src={URL.createObjectURL(file)}
                                                alt="Preview"
                                                width="25%"
                                                style={{marginRight: '10px'}}
                                            />
                                            <button className={styles.button} type="button"
                                                    onClick={() => handleRemoveFile(index)}>Удалить фото
                                            </button>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                        <div><PointsData points={points} setPoints={setPoints}/></div>
                        <button style={{transform: "translateX(7%)"}}
                            className={styles.button}
                            type="button"
                            onClick={handleClickAddPump}
                            disabled={!isFormValid()}
                        >
                            Сохранить
                        </button>
                    </Collapse>

                    <Collapse  in={visibleEngine}>
                        <div className={styles.selectWrapper}>
                            <div style={{marginTop: "10%"}} className={styles.pumpContainer}>
                                {/* Левый насос */}
                                <div className={`${styles.pumpSection} ${styles.pumpLeft}`}>
                                    <div className={styles.horizontalGroup}>
                                        <div style={{
                                            display: "flex",
                                            justifyContent: "center",
                                            alignItems: "center",
                                            height: "100%",
                                            transform: "translateX(-95px)"
                                        }} className={styles.formContent}>
                                            <div><EngineData index={0}
                                                             installationData={installationData}
                                                             isPump={true}
                                                             setInstallationData={setInstallationData}

                                            /></div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button style={{transform: "translateX(7%)"}}
                            className={styles.button}
                            type="button"
                            onClick={handleClickAddEngine}
                        >
                            Сохранить
                        </button>
                    </Collapse>
                </div>

            </div>

        </div>
    );
};

export default AdminPage;