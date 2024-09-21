import {Header} from "../../components/UI/Header";
import styles from "./installationChoice.module.css";
import {useState} from "react";
import arrow from "../../assets/next-page.svg";
import locationArrow from "../../assets/location-arrow.svg";
import {useNavigate} from "react-router-dom";

export const InstallationChoice = () => {
    const [installationType, setInstallationType] = useState("");
    const [hydromoduleType, setHydromoduleType] = useState("");
    const [workingPumps, setWorkingPumps] = useState(1);
    const [reservePumps, setReservePumps] = useState(1);
    const navigate = useNavigate();

    const handleArrowClick = async (e) => {
        e.preventDefault();

        if (!installationType) return;

        if (installationType === "hydra") {
            navigate("/selection/device_params_hydromodule");
        } else if (installationType === "hvs") {
            navigate("/selection/device_params_hvs");
        } else if (installationType === "pns") {
            if (hydromoduleType === "1") {
                navigate("/selection/device_params_pns_vpv");

            } else {
                navigate("/selection/device_params_pns_autp");
            }
        }
    };

    const handleInstallationTypeChange = (event) => {
        const value = event.target.value;
        setInstallationType(value);
        setHydromoduleType(""); // Сбрасываем значение при смене типа установки
    };

    const isFormComplete = () => {
        if (installationType === "hydra" && !hydromoduleType) {
            return false;
        }
        if (installationType === "hvs" && !hydromoduleType) {
            return false;
        }
        if (installationType === "pns" && !hydromoduleType) {
            return false;
        }
        return installationType && workingPumps && reservePumps;
    };

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Установка</h1>
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Тип установки</h2>
                            <div className={styles.selectWrapper}>
                                <select className={styles.select} onChange={handleInstallationTypeChange}>
                                    <option value="">Выберите тип установки</option>
                                    <option value="hydra">Гидромодуль</option>
                                    <option value="hvs">Хоз-пит</option>
                                    <option value="pns">ПНС</option>
                                </select>
                            </div>
                        </div>

                        {/* В зависимости от выбранного типа установки показываем дополнительные поля */}
                        {installationType === "hydra" && (
                            <div className={styles.formGroup}>
                                <h2 className={styles.formSubtitle}>Тип гидромодуля</h2>
                                <div className={styles.radioGroup}>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="1"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        С частотным управлением по давлению на выходе
                                    </label>
                                    <br/>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="2"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        С частотным управлением по перепаду давления
                                    </label>
                                    <br/>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="3"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        С релейным управлением по давлению на выходе
                                    </label>
                                    <br/>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="4"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        С релейным управлением по перепаду давления
                                    </label>
                                    <br/>
                                </div>
                            </div>
                        )}

                        {installationType === "hvs" && (
                            <div className={styles.formGroup}>
                                <h2 className={styles.formSubtitle}>Тип хоз-пит</h2>
                                <div className={styles.radioGroup}>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="1"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        С мульти-частотным регулированием (ПЧ на каждый насос)
                                    </label>
                                    <br/>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="2"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        С релейным управлением
                                    </label>
                                </div>
                            </div>
                        )}

                        {installationType === "pns" && (
                            <div className={styles.formGroup}>
                                <h2 className={styles.formSubtitle}>Тип ПНС</h2>
                                <div className={styles.radioGroup}>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="1"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        Установка для системы ВПВ
                                    </label>
                                    <br/>
                                    <label>
                                        <input
                                            type="radio"
                                            name="hydromodule"
                                            value="2"
                                            onChange={(e) => setHydromoduleType(e.target.value)}
                                        />{" "}
                                        Установка автоматического пожаротушения с жокей-насосом (АУПТ)
                                    </label>
                                </div>
                            </div>
                        )}

                        <div className={styles.formGroup}>
                            <h3 className={styles.formSubtitle}>Количество насосов</h3>
                            <div className={styles.radioGroup}>
                                <h4>Рабочих</h4>
                                <label>
                                    <input type="radio" name="workingPumps" value="1" defaultChecked={true}
                                           onChange={() => setWorkingPumps(1)}/> 1
                                </label>
                                <label>
                                    <input type="radio" name="workingPumps" value="2"
                                           onChange={() => setWorkingPumps(2)}/> 2
                                </label>
                                <label>
                                    <input type="radio" name="workingPumps" value="3"
                                           onChange={() => setWorkingPumps(3)}/> 3
                                </label>
                                <label>
                                    <input type="radio" name="workingPumps" value="4"
                                           onChange={() => setWorkingPumps(4)}/> 4
                                </label>
                            </div>
                            <div className={styles.radioGroup}>
                                <h4>Резервных</h4>
                                <label>
                                    <input type="radio" name="reservePumps" value="1" defaultChecked={true}
                                           onChange={() => setReservePumps(1)}/> 1
                                </label>
                                <label>
                                    <input type="radio" name="reservePumps" value="2"
                                           onChange={() => setReservePumps(2)}/> 2
                                </label>
                            </div>
                        </div>

                        <img
                            className={`${styles.arrow}`}
                            // className={`${styles.arrow} ${!isFormComplete() && styles.disabledArrow}`}
                            src={arrow}
                            onClick={isFormComplete() ? handleArrowClick : null}
                            alt="Далее"
                            title={!isFormComplete() ? "Выберите данные в каждом блоке" : ""}
                            style={!isFormComplete() ? {cursor: "not-allowed", transform: "none"} : {cursor: "pointer"}}
                        />
                    </div>

                    <div className={styles.rightSide}>
                        <div className={styles.titlesWrapper}>
                            <div className={`${styles.currentLocation}`}>
                                <h2>Установка</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Параметры прибора</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат подбора</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Дополнительные опции</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Данные об объекте</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};