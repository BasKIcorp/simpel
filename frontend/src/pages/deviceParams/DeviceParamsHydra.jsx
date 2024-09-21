import { Header } from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import { useState, useMemo } from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import { useNavigate } from "react-router-dom";

//TODO: fix margins
export const DeviceParamsHydra = () => {
    const navigate = useNavigate();

    // Состояния для полей формы
    const [fluidType, setFluidType] = useState('');
    const [temperature, setTemperature] = useState('');
    const [performance, setPerformance] = useState('');
    const [pressure, setPressure] = useState('');
    const [pumpType, setPumpType] = useState('');
    const [temperatureError, setTemperatureError] = useState('');

    // Валидация температуры
    const validateTemperature = (temp, fluid) => {
        let tempValue = parseFloat(temp);
        if (fluid === '1') { // Вода
            if (tempValue < 4 || tempValue > 70) {
                setTemperatureError('Для воды допустимый диапазон температуры: +4 … +70°C');
                return false;
            }
        } else if (fluid === '2' || fluid === '3') { // Растворы
            if (tempValue < -10 || tempValue > 70) {
                setTemperatureError('Для растворов допустимый диапазон температуры: -10 … +70°C');
                return false;
            }
        } else {
            setTemperatureError('Выберите тип теплоносителя');
            return false;
        }
        setTemperatureError('');
        return true;
    };
    const handleFluidTypeChange = (e) => {
        const selectedFluidType = e.target.value;
        setFluidType(selectedFluidType);
        // После изменения типа теплоносителя запускаем валидацию
        validateTemperature(temperature, selectedFluidType);
    };
    // Проверка, все ли поля заполнены и прошли валидацию
    const isFormComplete = useMemo(() => {
        return (
            fluidType &&
            temperature &&
            performance &&
            pressure &&
            pumpType &&
            validateTemperature(temperature, fluidType)
        );
    }, [fluidType, temperature, performance, pressure, pumpType]); // Dependencies for memoization

    const handleArrowClick = async (e) => {
        e.preventDefault();
        console.log(isFormComplete)
        if (isFormComplete) {
            navigate("/selection/selection_results");
        }
    };

    return (
        <div>
            <Header />
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Параметры прибора</h1>

                        {/* Тип теплоносителя */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Тип теплоносителя</h2>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input
                                        type="radio"
                                        name="hydromodule"
                                        value="1"
                                        onChange={handleFluidTypeChange}
                                    /> Вода
                                </label>
                                <br/>
                                <label>
                                    <input
                                        type="radio"
                                        name="hydromodule"
                                        value="2"
                                        onChange={handleFluidTypeChange}
                                    /> Водный раствор пропиленгликоля
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
                                    <input
                                        type="radio"
                                        name="hydromodule"
                                        value="3"
                                        onChange={handleFluidTypeChange}
                                    /> Водный раствор этиленгликоля
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

                        {/* Температура */}
                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle} >Температура</h2>
                            <div className={styles.temperatureInputWrapper}>
                                <input
                                    type="number"
                                    className={styles.temperatureInput}
                                    value={temperature}
                                    onChange={(e) => setTemperature(e.target.value)}
                                    onBlur={() => validateTemperature(temperature, fluidType)}
                                    style={temperatureError ? {borderColor: "red"} : {}}

                                />
                                <span className={styles.temperatureUnit}
                                      style={temperatureError ? {color: "red"} : {}}
                                >°C</span>
                            </div>
                            {temperatureError && <p className={styles.error}>{temperatureError}</p>}
                        </div>

                        {/* Суммарная производительность */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Суммарная производительность</h2>
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

                        {/* Требуемый напор */}
                        <div className={styles.temperatureGroup}>
                            <h2 className={styles.formSubtitle}>Требуемый напор</h2>
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

                        {/* Тип насосов */}
                        <div className={styles.formGroup}>
                            <h3 className={styles.formSubtitle}>Тип насосов</h3>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input
                                        type="radio"
                                        name="workingPumps"
                                        value="1"
                                        onChange={(e) => setPumpType(e.target.value)}
                                    /> Вертикальные
                                </label>
                                <br/>
                                <label>
                                    <input
                                        type="radio"
                                        name="workingPumps"
                                        value="2"
                                        onChange={(e) => setPumpType(e.target.value)}
                                    /> Горизонтальные
                                </label>
                            </div>
                        </div>

                        {/* Стрелка для перехода */}
                        <img
                            className={`${styles.arrow} ${!isFormComplete && styles.disabledArrow}`}
                            src={arrow}
                            onClick={handleArrowClick}
                            // onClick={isFormComplete ? handleArrowClick : null}
                            alt="Далее"
                            title={!isFormComplete ? "Выберите данные в каждом блоке" : ""}
                            style={!isFormComplete ? { cursor: "not-allowed" } : { cursor: "pointer" }}
                        />
                    </div>

                    <div className={styles.rightSide}>
                        <div className={styles.titlesWrapper}>
                            <div className={`${styles.titleWithArrow}`}>
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