import React, {useEffect, useState} from "react";
import styles from './result.module.css';
import {Header} from "../../components/UI/Header";
import Graph from "../../components/UI/Graph";
import testData from "../selection_results/test_data.json";
import {useSelector} from "react-redux";

function Result() {
    const [selectedImage, setSelectedImage] = useState("drawing1");
    const [selectedPump, setSelectedPump] = useState("SF-9050");
    const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - расход/напор, 2 - расход/мощность, 3 - расход/квитанционный запас
    const [graphData, setGraphData] = useState([]);
    const [legendNames, setLegendNames] = useState([]);
    const generalInfo = useSelector((state) => state.pump.generalInfo);
    const pumpData = useSelector((state) => state.pump.pumpData);
    const motorData = useSelector((state) => state.pump.motorData);
    const materials = useSelector((state) => state.pump.materials);
    const points = useSelector((state) => state.pump.points)
    const handleImageChange = (e) => {
        setSelectedImage(e.target.value);
    };


    useEffect(() => {
        if (!selectedPump) return;

        switch (selectedGraphType) {
            case "1": // Pressure/Flow graph
                if (Array.isArray(points.pointsPressure)) {
                    const pressureFlowData = points.pointsPressure.map(point => ({
                        name: point.id.toString(),
                        pressure: point.y,
                        flow: point.x,
                    }));
                    setGraphData(pressureFlowData);
                    setLegendNames([
                        {key: "flow", label: "Flow", color: "#82ca9d"},
                        {key: "pressure", label: "Pressure", color: "#8884d8"},
                    ]);
                } else {
                    setGraphData([]);
                    console.error('points.pointsPressure is not an array');
                }
                break;
            case "2": // Power/Flow graph
                if (Array.isArray(points.pointsPower)) {
                    const powerFlowData = points.pointsPower.map(point => ({
                        name: point.id.toString(),
                        power: point.y,
                        flow: point.x,
                    }));
                    setGraphData(powerFlowData);
                    setLegendNames([
                        {key: "flow", label: "Flow", color: "#82ca9d"},
                        {key: "power", label: "Power", color: "#ff7300"},
                    ]);
                } else {
                    setGraphData([]);
                    console.error('points.pointsPower is not an array');
                }
                break;
            case "3": // NPSH/Flow graph
                if (Array.isArray(points.pointsNPSH)) {
                    const npshFlowData = points.pointsNPSH.map(point => ({
                        name: point.id.toString(),
                        suctionReserve: point.y,
                        flow: point.x,
                    }));
                    setGraphData(npshFlowData);
                    setLegendNames([
                        {key: "flow", label: "Flow", color: "#82ca9d"},
                        {key: "suctionReserve", label: "NPSH", color: "#8884d8"},
                    ]);
                } else {
                    setGraphData([]);
                    console.error('points.pointsNPSH is not an array');
                }
                break;
            default:
                setGraphData([]);
                setLegendNames([]);
        }
    }, [selectedPump, selectedGraphType, points]);

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.header}>
                        <h1>Результат</h1>
                    </div>
                    <div className={styles.buttonContainer}>
                        <button className={styles.button}>
                            Скачать pdf
                        </button>
                        <button className={styles.button}>
                            Pdf на e-mail
                        </button>
                        <div className={styles.infoContainer}>
                            <div className={styles.infoBlock}>
                                <strong>Стоимость установки:</strong>
                                <span>1.000.000</span>
                            </div>
                            <div className={styles.infoBlock}>
                                <strong>Предполагаемый срок поставки:</strong>
                                <span>20.10.2024</span>
                            </div>
                        </div>
                    </div>

                    <div className={styles.contentContainer}>
                        {/* Таблица */}
                        <div className={styles.tableContainer}>
                            <table className={styles.resultTable}>
                                <thead>
                                <tr>
                                    <th>Общие данные</th>
                                    <th>Значение</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>Основной насос</td>
                                    <td>{generalInfo.mainPump || 'BL 8-6R'}</td>
                                </tr>
                                <tr>
                                    <td>Жидкость</td>
                                    <td>{generalInfo.liquid || 'Вода'}</td>
                                </tr>
                                <tr>
                                    <td>Рабочая температура</td>
                                    <td>{generalInfo.operatingTemperature || '4°C'}</td>
                                </tr>
                                <tr>
                                    <td>Тип насоса</td>
                                    <td>{generalInfo.pumpType || 'Вертикальный многоступенчатый'}</td>
                                </tr>
                                <tr>
                                    <td>Количество насосов</td>
                                    <td>{generalInfo.numberOfPumps || '3'}</td>
                                </tr>
                                <tr>
                                    <td>Рабочих</td>
                                    <td>{generalInfo.workingPumps || '2'}</td>
                                </tr>
                                <tr>
                                    <td>Резервных</td>
                                    <td>{generalInfo.reservePumps || '1'}</td>
                                </tr>
                                <tr>
                                    <td>Управление</td>
                                    <td>{generalInfo.controlType || 'Частотное управление'}</td>
                                </tr>
                                <tr>
                                    <td>Номинальная подача</td>
                                    <td>{generalInfo.ratedFlow || '11,93 м³/ч'}</td>
                                </tr>
                                <tr>
                                    <td>Номинальный напор</td>
                                    <td>{generalInfo.ratedPressure || '55,4 м вод. ст.'}</td>
                                </tr>
                                <tr>
                                    <td>Опции</td>
                                    <td>{generalInfo.options || 'нет'}</td>
                                </tr>
                                <tr>
                                    <th className={styles.tableTitle}>Данные насоса</th>
                                    <th className={styles.tableTitle}>Значение</th>
                                </tr>
                                <tr>
                                    <td>Производительность</td>
                                    <td>{pumpData.manufacturer || 'MAS DAF'}</td>
                                </tr>
                                <tr>
                                    <td>Скорость</td>
                                    <td>{pumpData.speed || '2900'}</td>
                                </tr>
                                <tr>
                                    <td>Количество ступеней</td>
                                    <td>{pumpData.numberOfStages || '06'}</td>
                                </tr>
                                <tr>
                                    <td>Максимальное давление</td>
                                    <td>{pumpData.maxPressure || '21'}</td>
                                </tr>
                                <tr>
                                    <td>Максимальный напор</td>
                                    <td>{pumpData.maxHead || '63'}</td>
                                </tr>
                                <tr>
                                    <td>Диаметр раб. колеса</td>
                                    <td>{pumpData.impellerDiameter || 'нет'}</td>
                                </tr>

                                <tr>
                                    <th className={styles.tableTitle}>Данные двигателя</th>
                                    <th className={styles.tableTitle}>Значение</th>
                                </tr>
                                <tr>
                                    <td>Производитель</td>
                                    <td>{motorData.manufacturer || 'IMP Pumps'}</td>
                                </tr>
                                <tr>
                                    <td>Исполнение</td>
                                    <td>{motorData.execution || 'IE3'}</td>
                                </tr>
                                <tr>
                                    <td>Тип</td>
                                    <td>{motorData.type || 'Вертикальный многоступенчатый'}</td>
                                </tr>
                                <tr>
                                    <td>Мощность</td>
                                    <td>{motorData.power || '2,2'}</td>
                                </tr>
                                <tr>
                                    <td>Сила тока</td>
                                    <td>{motorData.current || '4,72'}</td>
                                </tr>
                                <tr>
                                    <td>Напряжение</td>
                                    <td>{motorData.voltage || '380'}</td>
                                </tr>
                                <tr>
                                    <td>Обороты</td>
                                    <td>{motorData.speed || '2900'}</td>
                                </tr>
                                <tr>
                                    <td>Вид защиты</td>
                                    <td>{motorData.protectionType || 'IP55'}</td>
                                </tr>
                                <tr>
                                    <td>Класс изоляции</td>
                                    <td>{motorData.insulationClass || 'F'}</td>
                                </tr>
                                <tr>
                                    <td>Цвет</td>
                                    <td>{motorData.color || 'нет'}</td>
                                </tr>

                                <tr>
                                    <th className={styles.tableTitle}>Материалы</th>
                                    <th className={styles.tableTitle}>Значение</th>
                                </tr>
                                <tr>
                                    <td>Коллектор</td>
                                    <td>{materials.collector || 'Нержавеющая сталь'}</td>
                                </tr>
                                <tr>
                                    <td>Запорные клапаны</td>
                                    <td>{materials.shutOffValves || 'Чугун'}</td>
                                </tr>
                                <tr>
                                    <td>Обратные клапаны</td>
                                    <td>{materials.checkValves || 'Чугун'}</td>
                                </tr>
                                <tr>
                                    <td>Реле давления</td>
                                    <td>{materials.pressureRelay || 'Хромованадиевый цинковый сплав'}</td>
                                </tr>
                                <tr>
                                    <td>Датчики давления</td>
                                    <td>{materials.pressureSensors || 'AISI 316'}</td>
                                </tr>
                                <tr>
                                    <td>Заглушки, фланцы</td>
                                    <td>{materials.plugsFlanges || 'Нержавеющая сталь'}</td>
                                </tr>
                                <tr>
                                    <td>Стойка</td>
                                    <td>{materials.rack || 'Окрашенная сталь'}</td>
                                </tr>
                                <tr>
                                    <td>Рама-основание</td>
                                    <td>{materials.baseFrame || 'Окрашенная сталь'}</td>
                                </tr>
                                <tr>
                                    <td>Корпус насоса</td>
                                    <td>{materials.pumpBody || 'Чугун'}</td>
                                </tr>
                                <tr>
                                    <td>Внешний кожух</td>
                                    <td>{materials.outerCover || 'Чугун'}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        {/* Чертежи и графики */}
                        <div className={styles.imageGraphContainer}>
                            {/* Чертежи */}
                            <div className={styles.imageContainer}>
                                <h3>Чертежи</h3>
                                <img
                                    src={require(`../../assets/${selectedImage}.png`)}
                                    alt="Чертеж"
                                    className={styles.image}
                                />
                                <div className={styles.drawingSelector}>
                                    <label>
                                        <input
                                            type="radio"
                                            value="drawing1"
                                            checked={selectedImage === "drawing1"}
                                            onChange={handleImageChange}
                                        />
                                    </label>
                                    <label>
                                        <input
                                            type="radio"
                                            value="drawing2"
                                            checked={selectedImage === "drawing2"}
                                            onChange={handleImageChange}
                                        />
                                    </label>
                                    <label>
                                        <input
                                            type="radio"
                                            value="drawing3"
                                            checked={selectedImage === "drawing3"}
                                            onChange={handleImageChange}
                                        />
                                    </label>
                                </div>
                            </div>

                            {/* Графики */}
                            <div className={styles.chartContainer}>
                                <p className={styles.charMargin}>Графики</p>
                                <div className={styles.chart}>
                                    <Graph data={graphData} legendNames={legendNames}/>
                                </div>
                                <div className={styles.graphSelector}>
                                    <label>
                                        <input
                                            type="radio"
                                            name="graph"
                                            value="1"
                                            checked={selectedGraphType === "1"}
                                            onChange={() => setSelectedGraphType("1")}
                                        />
                                    </label>
                                    <label>
                                        <input
                                            type="radio"
                                            name="graph"
                                            value="2"
                                            checked={selectedGraphType === "2"}
                                            onChange={() => setSelectedGraphType("2")}
                                        />
                                    </label>
                                    <label>
                                        <input
                                            type="radio"
                                            name="graph"
                                            value="3"
                                            checked={selectedGraphType === "3"}
                                            onChange={() => setSelectedGraphType("3")}
                                        />
                                    </label>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    );
}

export default Result;