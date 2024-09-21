import { Header } from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import { useMemo, useState } from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import { useNavigate } from "react-router-dom";

export const DeviceParamsPNSAUTP = () => {
    const navigate = useNavigate();

    const [liquidType, setLiquidType] = useState('water');
    const [temperature, setTemperature] = useState('');
    const [performance, setPerformance] = useState('');
    const [performanceJokey, setPerformanceJokey] = useState('');
    const [pressure, setPressure] = useState('');
    const [pressureJokey, setPressureJokey] = useState('');
    const [pumpType, setPumpType] = useState('');
    const [performanceJokeyError, setPerformanceJokeyError] = useState('');
    const [pressureJokeyError, setPressureJokeyError] = useState('');

    // Validate performance of jockey pump (1 to 5 m³/h)
    function validatePerformanceJokey(value) {
        const performanceValue = parseFloat(value);
        if (performanceValue < 1 || performanceValue > 5) {
            setPerformanceJokeyError('Производительность жокей-насоса должна быть от 1 до 5 м³/ч');
            return false;
        }
        setPerformanceJokeyError('');
        return true;
    }

    // Validate pressure of jockey pump (greater than 2.4 м.вод.ст)
    function validatePressureJokey(value) {
        const pressureValue = parseFloat(value);
        if (pressureValue <= 2.4) {
            setPressureJokeyError('Напор жокей-насоса должен быть больше 2.4 м.вод.ст.');
            return false;
        }
        setPressureJokeyError('');
        return true;
    }

    // Check if form is complete
    const isFormComplete = useMemo(() => {
        return (
            temperature >= 4 && temperature <= 70 &&
            performance &&
            pressure &&
            validatePerformanceJokey(performanceJokey) &&
            validatePressureJokey(pressureJokey)
        );
    }, [temperature, performance, pressure, performanceJokey, pressureJokey]);

    const handleArrowClick = async (e) => {
        e.preventDefault();

        if (isFormComplete) {
            navigate("/selection/selection_results");
        }
    };

    const handleLiquidChange = (e) => {
        const selectedLiquid = e.target.value;
        setLiquidType(selectedLiquid);
        // Reset temperature if not water
        if (selectedLiquid !== 'water') {
            setTemperature('');
        }
    };

    return (
        <div>
            <Header />
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Параметры прибора</h1>

                        {liquidType === 'water' && (
                            <div className={styles.temperatureGroup} style={{ marginTop: 20 }}>
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
                        )}

                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Суммарная производительность основных насосов установки</h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={performance}
                                    onChange={(e) => setPerformance(e.target.value)}
                                />
                                <span className={styles.temperatureUnit}>м³/ч</span>
                            </div>
                        </div>

                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Требуемый напор основных насосов установки</h2>
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

                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Суммарная производительность жокей-насоса</h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input
                                    min={1}
                                    max={5}
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={performanceJokey}
                                    onChange={(e) => {
                                        setPerformanceJokey(e.target.value);
                                        validatePerformanceJokey(e.target.value);
                                    }}
                                />
                                {performanceJokeyError && <p className={styles.error}>{performanceJokeyError}</p>}
                                <span className={styles.temperatureUnit}>м³/ч</span>
                            </div>
                        </div>

                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Требуемый напор жокей насоса</h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input
                                    min={2.4}
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={pressureJokey}
                                    onChange={(e) => {
                                        setPressureJokey(e.target.value);
                                        validatePressureJokey(e.target.value);
                                    }}
                                />
                                {pressureJokeyError && <p className={styles.error}>{pressureJokeyError}</p>}
                                <span className={styles.temperatureUnit}>м.вод.ст.</span>
                            </div>
                        </div>

                        <img
                            className={`${styles.arrow} ${!isFormComplete && styles.disabledArrow}`}
                            src={arrow}
                            onClick={isFormComplete ? handleArrowClick : null}
                            alt="Далее"
                            title={!isFormComplete ? "Выберите данные в каждом блоке" : ""}
                            style={!isFormComplete ? { cursor: "not-allowed", transform: "none" } : { cursor: "pointer" }}
                        />
                    </div>

                    <div className={styles.rightSide}>
                        <div className={styles.titlesWrapper}>
                            <div className={styles.titleWithArrow}>
                                <h2>Установка</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка" />
                            </div>
                            <div className={styles.currentLocation}>
                                <h2>Параметры прибора</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка" />
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат подбора</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка" />
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Дополнительные опции</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка" />
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Данные об объекте</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка" />
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};