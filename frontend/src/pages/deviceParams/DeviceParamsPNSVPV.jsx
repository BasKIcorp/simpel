import {Header} from "../../components/UI/Header";
import styles from "./deviceParams.module.css";
import {useState} from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import {useNavigate} from "react-router-dom";
import {setGeneralInfo} from "../../store/pumpSlice";
import {useDispatch,useSelector} from "react-redux";
import {server_url} from "../../config"
export const DeviceParamsPNSVPV = () => {
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
    const [pressure, setPressure] = useState('');
    const [pumpType, setPumpType] = useState('');
    const dispatch = useDispatch();

    //первые правки
    const [isVerticalSelected, setIsVerticalSelected] = useState(false);
    const [isHorizontalSelected, setIsHorizontalSelected] = useState(false);

    const updatePumpType = (vertical, horizontal) => {
        if (vertical && horizontal) {
            setPumpType("BOTH");
        } else if (vertical) {
            setPumpType("VERTICAL");
        } else if (horizontal) {
            setPumpType("HORIZONTAL");
        } else {
            setPumpType("");
        }
    };

    const handleVerticalChange = (e) => {
        setIsVerticalSelected((prev) => {
            const newIsVerticalSelected = !prev;
            updatePumpType(newIsVerticalSelected, isHorizontalSelected);
            return newIsVerticalSelected;
        });
    };

    const handleHorizontalChange = (e) => {
        setIsHorizontalSelected((prev) => {
            const newIsHorizontalSelected = !prev;
            updatePumpType(isVerticalSelected, newIsHorizontalSelected);
            return newIsHorizontalSelected;
        });
    };

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
        dispatch(setGeneralInfo({ pumpTypeForSomeInstallation: pumpType }));
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
                                    <input type="radio" name="hydromodule" value="WATER"
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
                                    <input type="checkbox"
                                           onChange={handleVerticalChange}/>
                                    Вертикальные
                                </label>
                                <br/>
                                <label>
                                    <input type="checkbox"
                                           onChange={handleHorizontalChange}/>
                                    Горизонтальные
                                </label>
                            </div>
                        </div>

                        <img
                            className={`${styles.arrow} ${!isFormComplete() ? styles.disabledArrow : ''}`}
                            src={arrow}
                            onClick={handleArrowClick}
                            alt="Далее"
                            title={!isFormComplete() ? "Заполните все поля, чтобы продолжить" : ""}
                            style={!isFormComplete ? {cursor: "not-allowed", transform: "none"} : {cursor: "pointer"}}

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