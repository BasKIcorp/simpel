import {Header} from "../../components/UI/Header";
import styles from "./installationChoice.module.css";
import {useState} from "react";
import arrow from '../../assets/next-page.svg'
import locationArrow from '../../assets/location-arrow.svg'
import {replace, useNavigate} from "react-router-dom";

export const InstallationChoice = () => {
    const [installationType, setInstallationType] = useState('');
    const [hydromoduleType, setHydromoduleType] = useState('');
    const [workingPumps, setWorkingPumps] = useState(1);
    const [reservePumps, setReservePumps] = useState(1);
    const navigate = useNavigate();


    const handleArrowClick = async (e) => {
        e.preventDefault();
        navigate("/selection/device_params")
    }
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

                                <select className={styles.select}>
                                    <option value="">Выберите тип установки</option>
                                    <option value="type1">Гидромодуль</option>
                                    <option value="type2">ХВС</option>
                                    <option value="type2">ПМС</option>
                                </select>
                            </div>
                        </div>
                        {/*TODO: тут главное не забыть, что тут взависимости от выбора типа установки будут разные дивы, тип гидромодуля, тип хвс пмс вот это вот все, надо запомнить или прочитать это и потом сделать обязательно*/}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Тип гидромодуля</h2>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input type="radio" name="hydromodule" value="1"/> С частным регулированием
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="2"/> С релейным управлением
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="3"/> С кайфовым управлением
                                </label>
                            </div>
                        </div>
                        <div className={styles.formGroup}>
                            <h3 className={styles.formSubtitle}>Количество насосов</h3>
                            <div className={styles.radioGroup}>
                                <h4>Рабочих</h4>
                                <label>
                                    <input type="radio" name="workingPumps" value="1"/> 1
                                </label>
                                <label>
                                    <input type="radio" name="workingPumps" value="2"/> 2
                                </label>
                                <label>
                                    <input type="radio" name="workingPumps" value="3"/> 3
                                </label>
                                <label>
                                    <input type="radio" name="workingPumps" value="4"/> 4
                                </label>
                            </div>
                            <div className={styles.radioGroup}>
                                <h4>Резервных</h4>
                                <label>
                                    <input type="radio" name="reservePumps" value="1"/> 1
                                </label>
                                <label>
                                    <input type="radio" name="reservePumps" value="2"/> 2
                                </label>
                            </div>
                        </div>
                        {/*TODO: здесь не забыть сделать navigate To */}
                        <img className={styles.arrow} src={arrow} onClick={handleArrowClick}/>
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
                                <h2>Результат подбора
                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Дополнительные опции

                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Данные об объекте
                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат
                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
