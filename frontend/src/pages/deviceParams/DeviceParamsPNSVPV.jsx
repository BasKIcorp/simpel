import {Header} from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import {useState} from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import {useNavigate} from "react-router-dom";
import {setGeneralInfo} from "../../store/pumpSlice";
import {useDispatch} from "react-redux";

export const DeviceParamsPNSVPV = () => {
    const navigate = useNavigate();

    const [liquidType, setLiquidType] = useState('WATER');
    const [temperature, setTemperature] = useState('');
    const [performance, setPerformance] = useState('');
    const [pressure, setPressure] = useState('');
    const [pumpType, setPumpType] = useState('');
    const dispatch = useDispatch();

    // Отключить кнопку, если форма не заполнена полностью
    const isFormComplete = () => {
        return (
            liquidType &&
            temperature >= 4 && temperature <= 70 &&
            performance &&
            pressure &&
            pumpType
        );
    };

    const handleArrowClick = async (e) => {
        e.preventDefault();
        dispatch(setGeneralInfo({ liquid: liquidType }));
        dispatch(setGeneralInfo({ operatingTemperature: temperature }));
        dispatch(setGeneralInfo({ ratedPressure: pressure }));
        dispatch(setGeneralInfo({ ratedFlow: performance }));
        dispatch(setGeneralInfo({ pumpType: pumpType }));
        if (isFormComplete()) {
            navigate("/selection/selection_results");
        }
    };

    const handleLiquidChange = (e) => {
        const selectedLiquid = e.target.value;
        setLiquidType(selectedLiquid);
        // Сбросить температуру, если выбрана не вода
        if (selectedLiquid !== 'WATER') {
            setTemperature('');
        }
    };

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Параметры прибора</h1>

                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Тип прокачиваемой жидкости</h2>
                            <div className={styles.radioGroup}>
                                {/*<label>*/}
                                {/*    <input*/}
                                {/*        type="radio"*/}
                                {/*        name="hydromodule"*/}
                                {/*        onChange={handleLiquidChange}*/}
                                {/*    />*/}
                                {/*    Вода*/}
                                {/*</label>*/}
                                <label>
                                    <input type="radio" name="hydromodule" value="1"
                                           onChange={handleLiquidChange}/> Вода
                                </label>
                            </div>
                        </div>


                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Температура воды</h2>
                            {/*(4°C – 50°C)*/}
                            <div className={styles.temperatureInputWrapper}>
                                <input
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={temperature}
                                    onChange={(e) => setTemperature(e.target.value)}
                                    min="4"
                                    max="50"
                                />
                                <span className={styles.temperatureUnit}>°C</span>
                            </div>
                        </div>


                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Суммарная производительность установки</h2>
                            <div className={styles.perfomanceInputWrapper}>
                                <input
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={performance}
                                    onChange={(e) => setPerformance(e.target.value)}
                                />
                                <span className={styles.temperatureUnit}>м3/ч</span>
                            </div>
                        </div>

                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Требуемый напор </h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={pressure}
                                    onChange={(e) => setPressure(e.target.value)}
                                />
                                <span className={styles.temperatureUnit}>м.вод.ст.</span>
                            </div>
                        </div>

                        <div className={styles.formGroup}>
                            <h3 className={styles.formSubtitle}>Тип насосов</h3>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input type="radio" name="workingPumps" value="1"
                                           onChange={(e) => setPumpType(e.target.value)}
                                    /> Вертикальные
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="workingPumps" value="2"
                                           onChange={(e) => setPumpType(e.target.value)}
                                    /> Горизонтальные
                                </label>
                            </div>
                        </div>

                        <img
                            className={`${styles.arrow} ${!isFormComplete() ? styles.disabledArrow : ''}`}
                            src={arrow}
                            onClick={handleArrowClick}
                            alt="Далее"
                            title={!isFormComplete() ? "Заполните все поля, чтобы продолжить" : ""}
                            style={!isFormComplete ? { cursor: "not-allowed", transform: "none" } : { cursor: "pointer" }}

                        />
                    </div>

                    <div className={styles.rightSide}>
                        <div className={styles.titlesWrapper}>
                            <div className={styles.titleWithArrow}>
                                <h2>Установка</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.currentLocation}>
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