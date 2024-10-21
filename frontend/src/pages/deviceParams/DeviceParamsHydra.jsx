import { Header } from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import { useState, useMemo,useEffect} from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import { useNavigate } from "react-router-dom";
import { setGeneralInfo } from "../../store/pumpSlice";
import { useDispatch, useSelector } from "react-redux";
import {server_url} from "../../config"

export const DeviceParamsHydra = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    // Получаем generalInfo из Redux
    const generalInfo = useSelector((state) => state.pump.generalInfo);

    // Состояния для полей формы
    const [selectedInstallation, setSelectedInstallation] = useState(null); // Храним всю установку
    const [selectedPump, setSelectedPump] = useState(null); // Храним выбранный насос
    const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - pressure/flow, 2 - power/flow, 3 - NPSH/flow
    const [graphData, setGraphData] = useState([]);
    const [legendNames, setLegendNames] = useState([]);
    const [installations, setInstallations] = useState([]); // Храним все установки
    const token = useSelector((state) => state.user.token);

    const [fluidType, setFluidType] = useState('');
    const [temperature, setTemperature] = useState('');
    const [performance, setPerformance] = useState(0);
    const [pressure, setPressure] = useState(0);
    const [pumpType, setPumpType] = useState('');
    const [temperatureError, setTemperatureError] = useState('');

    // Валидация температуры
    const validateTemperature = (temp, fluid) => {
        let tempValue = parseFloat(temp);
        if (fluid === 'WATER') {
            if (tempValue < 4 || tempValue > 70) {
                setTemperatureError('Для воды допустимый диапазон температуры: +4 … +70°C');
                return false;
            }
        } else if (fluid === 'PROPYLENE_GLYCOL' || fluid === 'ETHYLENE_GLYCOL') {
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
        validateTemperature(temperature, selectedFluidType);
    };

    const isFormComplete = useMemo(() => {
        return (
            fluidType &&
            temperature &&
            performance &&
            pressure &&
            pumpType &&
            validateTemperature(temperature, fluidType)
        );
    }, [fluidType, temperature, performance, pressure, pumpType]);

    // Функция для отправки запроса


    const handleArrowClick = async (e) => {
        e.preventDefault();

        // Сохраняем введенные значения в Redux
        dispatch(setGeneralInfo({ liquid: fluidType }));
        dispatch(setGeneralInfo({ operatingTemperature: temperature }));
        dispatch(setGeneralInfo({ ratedPressure: pressure }));
        dispatch(setGeneralInfo({ ratedFlow: performance }));

        console.log("Параметры для запроса:", generalInfo);
        console.log({
            typeInstallations: generalInfo.installationType,
            subtype: generalInfo.subType,
            coolantType: generalInfo.liquid,
            temperature: generalInfo.operatingTemperature,
            countMainPumps: generalInfo.workingPumps,
            countSparePumps: generalInfo.reservePumps,
            flowRate: parseInt(generalInfo.ratedFlow),
            pressure: parseInt(generalInfo.ratedPressure),

        });

        if (isFormComplete) {
            const pumpData = await fetchPumpData();
            if (pumpData) {
                // Переход на следующую страницу только после успешного получения данных
                navigate("/selection/selection_results");
            } else {
                // Здесь можно обработать случай, когда данные не получены
                alert("Не было найдено установок с такими параметрами, попробуйте изменить их");
            }
        }


    };
    const fetchPumpData = async () => {
        try {
            const request = JSON.stringify({
                typeInstallations: generalInfo.installationType,
                subtype: generalInfo.subType,
                coolantType: fluidType,
                temperature: temperature,
                countMainPumps: generalInfo.workingPumps,
                countSparePumps: generalInfo.reservePumps,
                flowRate: parseInt(performance),
                pressure: parseInt(pressure),
                pumpTypeForSomeInstallation: ""
            });
            console.log(request);

            const response = await fetch(`${server_url}/api/simple/inst/get`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: request,
            });

            if (response.status === 403) {
                console.log("Redirecting to /auth due to 403");
                dispatch({ type: 'remove_user', payload: {username: "", token: ""}});
                localStorage.removeItem("token");
                navigate('/auth'); // Используем navigate вместо window.location.href
                return;
            }
            if (!response.ok ) {
                throw new Error(`Error: ${response.status}`);
            }


            const data = await response.json();
            if (!data || data.length === 0) {
                throw new Error("No installations found");
            }
            // Возвращаем данные, чтобы их можно было использовать в обработчике
            return data;

        } catch (error) {
            console.error("Failed to fetch pump data:", error);
            return null; // Возвращаем null в случае ошибки
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
                                        value="WATER"
                                        onChange={handleFluidTypeChange}
                                    /> Вода
                                </label>
                                <br/>
                                <label>
                                    <input
                                        type="radio"
                                        name="hydromodule"
                                        value="PROPYLENE_GLYCOL"
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
                                        value="ETHYLENE_GLYCOL"
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
                                        value="VERTICAL"
                                        onChange={(e) => setPumpType(e.target.value)}
                                    /> Вертикальные
                                </label>
                                <br/>
                                <label>
                                    <input
                                        type="radio"
                                        name="workingPumps"
                                        value="HORIZONTAL"
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