import React, {useEffect, useState} from "react";
import styles from './result.module.css';
import {Header} from "../../components/UI/Header";
import Graph from "../../components/UI/Graph";
import testData from "../selection_results/test_data.json";

function Result() {
    const [selectedImage, setSelectedImage] = useState("drawing1");
    const [selectedPump, setSelectedPump] = useState("SF-9050");
    const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - расход/напор, 2 - расход/мощность, 3 - расход/квитанционный запас
    const [graphData, setGraphData] = useState([]);
    const [legendNames, setLegendNames] = useState([]);

    const handleImageChange = (e) => {
        setSelectedImage(e.target.value);
    };


    useEffect(() => {
        const selectedPumpData = testData[selectedPump];

        if (!selectedPumpData) return;

        switch (selectedGraphType) {
            case "1": // График расход/напор
                setGraphData(selectedPumpData.pressureFlow);
                setLegendNames([
                    {key: "flow", label: "Расход", color: "#82ca9d"},
                    {key: "pressure", label: "Напор", color: "#8884d8"},
                ]);
                break;
            case "2": // График расход/мощность
                setGraphData(selectedPumpData.powerFlow);
                setLegendNames([
                    {key: "flow", label: "Расход", color: "#82ca9d"},
                    {key: "power", label: "Мощность", color: "#ff7300"},
                ]);
                break;
            case "3": // График расход/квитанционный запас
                setGraphData(selectedPumpData.suctionReserveFlow);
                setLegendNames([
                    {key: "flow", label: "Расход", color: "#82ca9d"},
                    {key: "suctionReserve", label: "Квитанционный запас", color: "#8884d8"},
                ]);
                break;
            default:
                setGraphData([]);
                setLegendNames([]);
        }
    }, [selectedPump, selectedGraphType]);

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
                                    <td>BL 8-6R</td>
                                </tr>
                                <tr>
                                    <td>Жидкость</td>
                                    <td>Вода</td>
                                </tr>
                                <tr>
                                    <td>Рабочая температура</td>
                                    <td>4°C</td>
                                </tr>
                                <tr>
                                    <td>Тип насоса</td>
                                    <td>Вертикальный многоступенчатый</td>
                                </tr>
                                <tr>
                                    <td>Количество насосов</td>
                                    <td>3</td>
                                </tr>
                                <tr>
                                    <td>Рабочих</td>
                                    <td>2</td>
                                </tr>
                                <tr>
                                    <td>Резервных</td>
                                    <td>1</td>
                                </tr>
                                <tr>
                                    <td>Управление</td>
                                    <td>Частотное управление</td>
                                </tr>
                                <tr>
                                    <td>Номинальная подача</td>
                                    <td>11,93 м³/ч</td>
                                </tr>
                                <tr>
                                    <td>Номинальный напор</td>
                                    <td>55,4 м вод. ст.</td>
                                </tr>
                                <tr>
                                    <td>Опции</td>
                                    <td>нет</td>
                                </tr>
                                <tr>
                                    <th className={styles.tableTitle}>Данные насоса</th>
                                    <th className={styles.tableTitle}>Значение</th>
                                </tr>
                                <tr>
                                    <td>Производительность</td>
                                    <td>MAS DAF</td>
                                </tr>
                                <tr>
                                    <td>Скорость</td>
                                    <td>2900</td>
                                </tr>
                                <tr>
                                    <td>Количество ступеней</td>
                                    <td>06</td>
                                </tr>
                                <tr>
                                    <td>Максимальное давление</td>
                                    <td>21</td>
                                </tr>
                                <tr>
                                    <td>Максимальный напор</td>
                                    <td>63</td>
                                </tr>
                                <tr>
                                    <td>Диаметр раб. колеса</td>
                                    <td>нет</td>
                                </tr>

                                <tr>
                                    <th className={styles.tableTitle}>Данные двигателя</th>
                                    <th className={styles.tableTitle}>Значение</th>
                                </tr>
                                <tr>
                                    <td>Производитель</td>
                                    <td>IMP Pumps</td>
                                </tr>
                                <tr>
                                    <td>Исполнение</td>
                                    <td>IE3</td>
                                </tr>
                                <tr>
                                    <td>Тип</td>
                                    <td>Вертикальный многоступенчатый</td>
                                </tr>
                                <tr>
                                    <td>Мощность</td>
                                    <td>2,2</td>
                                </tr>
                                <tr>
                                    <td>Сила тока</td>
                                    <td>4,72</td>
                                </tr>
                                <tr>
                                    <td>Напряжение</td>
                                    <td>380</td>
                                </tr>
                                <tr>
                                    <td>Обороты</td>
                                    <td>2900</td>
                                </tr>
                                <tr>
                                    <td>Вид защиты</td>
                                    <td>IP55</td>
                                </tr>
                                <tr>
                                    <td>Класс изоляции</td>
                                    <td>F</td>
                                </tr>
                                <tr>
                                    <td>Цвет</td>
                                    <td>нет</td>
                                </tr>

                                <tr>
                                    <th className={styles.tableTitle}>Материалы</th>
                                    <th className={styles.tableTitle}>Значение</th>
                                </tr>
                                <tr>
                                    <td>Коллектор</td>
                                    <td>Нержавеющая сталь</td>
                                </tr>
                                <tr>
                                    <td>Запорные клапаны</td>
                                    <td>Чугун</td>
                                </tr>
                                <tr>
                                    <td>Обратные клапаны</td>
                                    <td>Чугун</td>
                                </tr>
                                <tr>
                                    <td>Реле давления</td>
                                    <td>Хромованадиевый цинковый сплав</td>
                                </tr>
                                <tr>
                                    <td>Датчики давления</td>
                                    <td>AISI 316</td>
                                </tr>
                                <tr>
                                    <td>Заглушки, фланцы</td>
                                    <td>Нержавеющая сталь</td>
                                </tr>
                                <tr>
                                    <td>Стойка</td>
                                    <td>Окрашенная сталь</td>
                                </tr>
                                <tr>
                                    <td>Рама-основание</td>
                                    <td>Окрашенная сталь</td>
                                </tr>
                                <tr>
                                    <td>Корпус насоса</td>
                                    <td>Чугун</td>
                                </tr>
                                <tr>
                                    <td>Внешний кожух</td>
                                    <td>Чугун</td>
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