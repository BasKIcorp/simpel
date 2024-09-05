import React, { useState, useEffect } from "react";
import styles from './result.module.css';
import { Header } from "../../components/UI/Header";
import arrow from "../../assets/next-page.svg";
import { useNavigate } from "react-router-dom";
import Graph from "../../components/UI/Graph";
import testData from './test_data.json'; // Импортируем данные из test_data.json

function SelectionResults() {
    const navigate = useNavigate();

    const [selectedPump, setSelectedPump] = useState("SF-9050");
    const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - расход/напор, 2 - расход/мощность, 3 - расход/квитанционный запас
    const [graphData, setGraphData] = useState([]);
    const [legendNames, setLegendNames] = useState([]);

    const handleArrowClick = async (e) => {
        e.preventDefault();
        navigate("/selection/additional_options");
    };

    // Метод для загрузки данных и обновления графика
    const getGraphsData = () => {
        const selectedPumpData = testData[selectedPump];

        if (!selectedPumpData) return;

        switch (selectedGraphType) {
            case "1": // График расход/напор
                setGraphData(selectedPumpData.pressureFlow);
                setLegendNames([{ key: "flow", color: "#82ca9d" },{ key: "pressure", color: "#8884d8" }]);
                break;
            case "2": // График расход/мощность
                setGraphData(selectedPumpData.powerFlow);
                setLegendNames([ { key: "flow", color: "#82ca9d" }, { key: "power", color: "#ff7300" }]);
                break;
            case "3": // График расход/квитанционный запас
                setGraphData(selectedPumpData.suctionReserveFlow);
                setLegendNames([{ key: "flow", color: "#82ca9d" },{ key: "suctionReserve", color: "#8884d8" }]);
                break;
            default:
                setGraphData([]);
                setLegendNames([]);
        }
    };

    // Обновляем график при изменении установки или типа графика
    useEffect(() => {
        const selectedPumpData = testData[selectedPump];

        if (!selectedPumpData) return;

        switch (selectedGraphType) {
            case "1": // График расход/напор
                setGraphData(selectedPumpData.pressureFlow);
                setLegendNames([
                    { key: "flow", label: "Расход", color: "#82ca9d" },
                    { key: "pressure", label: "Напор", color: "#8884d8" },
                ]);
                break;
            case "2": // График расход/мощность
                setGraphData(selectedPumpData.powerFlow);
                setLegendNames([
                    { key: "flow", label: "Расход", color: "#82ca9d" },
                    { key: "power", label: "Мощность", color: "#ff7300" },
                ]);
                break;
            case "3": // График расход/квитанционный запас
                setGraphData(selectedPumpData.suctionReserveFlow);
                setLegendNames([
                    { key: "flow", label: "Расход", color: "#82ca9d" },
                    { key: "suctionReserve", label: "Квитанционный запас", color: "#8884d8" },
                ]);
                break;
            default:
                setGraphData([]);
                setLegendNames([]);
        }
    }, [selectedPump, selectedGraphType]);

    return (
        <div>
            <Header /><div className={styles.wrapper}>
            <div className={styles.rectangle}>
                <div className={styles.header}>
                    <h1>Результат подбора</h1>
                </div>
                <div className={styles.content}>
                    <div className={styles.chartContainer}>
                        <div className={styles.selectWrapper}>
                            <select
                                className={styles.select}
                                value={selectedPump}
                                onChange={(e) => setSelectedPump(e.target.value)}
                            >
                                <option value="SF-9050">Насосная установка SF-9050</option>
                                <option value="SF-5090">Насосная установка SF-5090</option>
                                <option value="SF-8050">Насосная установка SF-8050</option>
                            </select>
                        </div>
                        <div className={styles.chart}>
                            <Graph data={graphData} legendNames={legendNames} />
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
                    <div className={styles.data}>
                        {/* Данные о выбранной установке */}
                        <h2>Данные о приборе</h2>
                        <p>Название: {selectedPump}</p>
                        <p>Количество насосов:</p>
                        <ul>
                            <li>Рабочих: 10</li>
                            <li>Резервных: 10</li>
                        </ul>
                        <p>Тип управления: частотное</p>
                        <p>Тип электропитания: тайп-си</p>
                        <p>Электрическая мощность каждого насоса: 90 кВт</p>
                        <p>Суммарная мощность: 2,2</p>
                        <p>Сила тока для каждого насоса: 4 А</p>
                        <p>Суммарная сила тока: 12 А</p>
                        <p>Диаметр коллектора: 2 мм</p>
                    </div>
                </div>
                <img className={styles.arrow} src={arrow} onClick={handleArrowClick} />
            </div>
        </div>
        </div>
    );
}
export default SelectionResults;