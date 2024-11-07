// InstallationForm.js - основной компонент для выбора установки
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { setGeneralInfo } from "../../store/pumpSlice";
import PumpDataForm from "./PumpDataForm";
import PhotoAndPointsForm from "./PhotoAndPointsForm";
import styles from "./AdminPage.module.css";
import {Header} from "../../components/UI/Header";
import {GMFields} from './GMFields'
import {HozPitPns} from './HozPitPns'

export const AdminPage = () => {
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
        pumpIds: [{id: 0}],
        engineIds: [{id: 0}],
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
            dmIn: 0,
            dmOut: 0,
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
            color: ''
        }],
        material: []
    });

    // Функция для изменения типа установки
    const handleInstallationTypeChange = (event) => {
        const value = event.target.value;
        console.log(value);
        setInstallationData(prevData => ({
            ...prevData,
            typeInstallations: value,
            subtype: "" // Сбрасываем подтип при изменении типа
        }));
    };

    // Функция для изменения подтипа установки
    const handleInstallationSubtypeChange = (event) => {
        const value = event.target.value;
        setInstallationData(prevData => ({
            ...prevData,
            subtype: value
        }));
    };

    // Функция для изменения данных установки
    const handleChange = (e) => {
        const { name, value } = e.target;
        setInstallationData({
            ...installationData,
            [name]: value
        });
        console.log(installationData)
    };

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>

                <div className={styles.rectangle}>

                    <div className={styles.formGroup}>
                        <h2 className={styles.formSubtitle}>Тип установки</h2>
                        <div className={styles.selectWrapper}>
                            <select className={styles.select} onChange={handleInstallationTypeChange}>
                                <option value="">Выберите тип установки</option>
                                <option title="Установка для циркуляционных систем" value="GM">Выносной гидромодуль
                                </option>
                                <option value="HOZPIT">Установка повышения давления</option>
                                <option value="PNS">Установка системы пожаротушения</option>
                            </select>
                            {console.log(installationData)}
                        </div>
                    </div>

                    <div>
                        {installationData.typeInstallations === "GM" && (
                            <div className={styles.formGroup}>
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
                            <div className={styles.formGroup}>
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
                            <div className={styles.formGroup}>
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
                    <div className={styles.formGroup}>
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
                        <div className={styles.selectWrapper}>
                            <h2 className={styles.formSubtitle}>Тип подключения</h2>
                            <select className={styles.select} name='powerType' onChange={handleChange}>
                                <option value="">Выберите тип подключения</option>
                                <option value="З80">З80</option>
                                <option value="З">З</option>
                            </select>
                        </div>

                        <div className={styles.selectWrapper}>
                            <h2 className={styles.formSubtitle}>Тип управления</h2>
                            <select className={styles.select} name='controlType' onChange={handleChange}>
                                <option value="">Выберите тип управления</option>
                                <option value="asd">asd</option>
                                <option value="qwe">qwe</option>
                            </select>
                        </div>
                    </div>
                    <div><GMFields installationData={installationData} setInstallationData={setInstallationData}/></div>
                    <div><HozPitPns installationData={installationData} setInstallationData={setInstallationData}/></div>
                </div>

            </div>

        </div>
    );
};

export default AdminPage;