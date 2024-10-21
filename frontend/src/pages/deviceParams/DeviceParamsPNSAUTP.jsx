import { Header } from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import { useMemo, useState } from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import { useNavigate } from "react-router-dom";
import {useDispatch,useSelector} from "react-redux";
import {setGeneralInfo} from "../../store/pumpSlice";
import {server_url} from "../../config"
export const DeviceParamsPNSAUTP = () => {
    const navigate = useNavigate();

    const generalInfo = useSelector((state) => state.pump.generalInfo);

    const [selectedInstallation, setSelectedInstallation] = useState(null); // Храним всю установку
    const [selectedPump, setSelectedPump] = useState(null); // Храним выбранный насос
    const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - pressure/flow, 2 - power/flow, 3 - NPSH/flow
    const [graphData, setGraphData] = useState([]);
    const [legendNames, setLegendNames] = useState([]);
    const [installations, setInstallations] = useState([]); // Храним все установки
    const token = useSelector((state) => state.user.token);

    const [liquidType, setLiquidType] = useState('WATER');
    const [temperature, setTemperature] = useState('');
    const [performance, setPerformance] = useState('');
    const [performanceJokey, setPerformanceJokey] = useState('');
    const [pressure, setPressure] = useState('');
    const [pressureJokey, setPressureJokey] = useState('');
    const [pumpType, setPumpType] = useState('');
    const [performanceJokeyError, setPerformanceJokeyError] = useState('');
    const [pressureJokeyError, setPressureJokeyError] = useState('');
    const dispatch = useDispatch();

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
    const fetchPumpData = async () => {
        try {
            const request = JSON.stringify({
                typeInstallations: generalInfo.installationType,
                subtype: generalInfo.subType,
                coolantType: "WATER",
                temperature: temperature,
                countMainPumps: generalInfo.workingPumps,
                countSparePumps: generalInfo.reservePumps,
                flowRate: parseInt(performance),
                pressure: parseInt(pressure),
                pumpTypeForSomeInstallation: pumpType
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

            if (!response.ok) {
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
    const handleArrowClick = async (e) => {
        e.preventDefault();
        dispatch(setGeneralInfo({ liquid: liquidType }));
        dispatch(setGeneralInfo({ operatingTemperature: temperature }));
        dispatch(setGeneralInfo({ ratedPressure: pressure }));
        dispatch(setGeneralInfo({ ratedFlow: performance }));
        dispatch(setGeneralInfo({ pumpType: pumpType }));
        dispatch(setGeneralInfo({ totalCapacityOfJockeyPump: performanceJokey }));
        dispatch(setGeneralInfo({ requiredJockeyPumpPressure: pressureJokey }));
        if (isFormComplete) {
            // Вызов функции отправки запроса и ждем его выполнения
            const pumpData = await fetchPumpData();

            // Проверяем, получили ли мы данные
            if (pumpData) {
                // Переход на следующую страницу только после успешного получения данных
                navigate("/selection/selection_results");
            } else {
                // Здесь можно обработать случай, когда данные не получены
                alert("Не было найдено установок с такими параметрами, попробуйте изменить их");
            }
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

                        {liquidType === 'WATER' && (
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