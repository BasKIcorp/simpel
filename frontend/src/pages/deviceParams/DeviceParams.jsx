import {Header} from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import {useState} from "react";
import arrow from '../../assets/next-page.svg'
import locationArrow from '../../assets/location-arrow.svg'
import {useNavigate} from "react-router-dom";
//TODO: fix margins
export const DeviceParams = () => {
    const navigate = useNavigate();

    const handleArrowClick = async (e) => {
        e.preventDefault();
        navigate("/selection/selection_results")
    }
    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Параметры прибора</h1>
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Тип теплоносителя</h2>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input type="radio" name="hydromodule" value="1"/> Вода
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="2"/> Водный раствор пропиленгликоля
                                    <select className={styles.concentraitionSelect}>
                                        <option value="10">10%</option>
                                        <option value="20">20%</option>
                                        <option value="30">30%</option>
                                        <option value="40">40%</option>
                                        <option value="50">50%</option>
                                    </select>
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="3"/> Водный раствор этиленгликоля
                                    <select className={styles.concentraitionSelect}>
                                        <option value="10">10%</option>
                                        <option value="20">20%</option>
                                        <option value="30">30%</option>
                                        <option value="40">40%</option>
                                        <option value="50">50%</option>
                                    </select>
                                </label>
                            </div>
                        </div>
                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Температура</h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input type="number" className={styles.temperatureInput}/>
                                <span className={styles.temperatureUnit}>°C</span>
                            </div>
                        </div>
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Суммарная производительность</h2>
                            <div className={styles.perfomanceInputWrapper}>
                                <input type="number" className={styles.temperatureInput}/>
                                <span className={styles.temperatureUnit}>м3/ч</span>
                            </div>
                        </div>
                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Требуемый напор</h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input type="number" className={styles.temperatureInput}/>
                                <span className={styles.temperatureUnit}>м.вод.ст.</span>
                            </div>
                        </div>
                        <div className={styles.formGroup}>
                            <h3 className={styles.formSubtitle}>Тип насосов</h3>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input type="radio" name="workingPumps" value="1"/> Вертикальные
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="workingPumps" value="2"/> Горизонтальные
                                </label>
                            </div>
                        </div>
                        {/*TODO: здесь не забыть сделать navigate To */}
                        <img className={styles.arrow} src={arrow} onClick={handleArrowClick}/>
                    </div>
                    <div className={styles.rightSide}>
                        <div className={styles.titlesWrapper}>
                            <div className={`${styles.titleWithArrow}`}>
                                <h2>Установка</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.currentLocation}>
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
