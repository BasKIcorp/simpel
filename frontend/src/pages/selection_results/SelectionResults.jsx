// import React, { useState, useEffect } from "react";
// import styles from './result.module.css';
// import { Header } from "../../components/UI/Header";
// import arrow from "../../assets/next-page.svg";
// import { useNavigate } from "react-router-dom";
// import Graph from "../../components/UI/Graph";
// import testData from './test_data.json'; // Импортируем данные из test_data.json
//
// function SelectionResults() {
//     const navigate = useNavigate();
//
//     const [selectedPump, setSelectedPump] = useState("SF-9050");
//     const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - расход/напор, 2 - расход/мощность, 3 - расход/квитанционный запас
//     const [graphData, setGraphData] = useState([]);
//     const [legendNames, setLegendNames] = useState([]);
//
//     const handleArrowClick = async (e) => {
//         e.preventDefault();
//         navigate("/selection/additional_options");
//     };
//
//     // Метод для загрузки данных и обновления графика
//     const getGraphsData = () => {
//         const selectedPumpData = testData[selectedPump];
//
//         if (!selectedPumpData) return;
//
//         switch (selectedGraphType) {
//             case "1": // График расход/напор
//                 setGraphData(selectedPumpData.pressureFlow);
//                 setLegendNames([{ key: "flow", color: "#82ca9d" },{ key: "pressure", color: "#8884d8" }]);
//                 break;
//             case "2": // График расход/мощность
//                 setGraphData(selectedPumpData.powerFlow);
//                 setLegendNames([ { key: "flow", color: "#82ca9d" }, { key: "power", color: "#ff7300" }]);
//                 break;
//             case "3": // График расход/квитанционный запас
//                 setGraphData(selectedPumpData.suctionReserveFlow);
//                 setLegendNames([{ key: "flow", color: "#82ca9d" },{ key: "suctionReserve", color: "#8884d8" }]);
//                 break;
//             default:
//                 setGraphData([]);
//                 setLegendNames([]);
//         }
//     };
//
//     // Обновляем график при изменении установки или типа графика
//     useEffect(() => {
//         const selectedPumpData = testData[selectedPump];
//
//         if (!selectedPumpData) return;
//
//         switch (selectedGraphType) {
//             case "1": // График расход/напор
//                 setGraphData(selectedPumpData.pressureFlow);
//                 setLegendNames([
//                     { key: "flow", label: "Расход", color: "#82ca9d" },
//                     { key: "pressure", label: "Напор", color: "#8884d8" },
//                 ]);
//                 break;
//             case "2": // График расход/мощность
//                 setGraphData(selectedPumpData.powerFlow);
//                 setLegendNames([
//                     { key: "flow", label: "Расход", color: "#82ca9d" },
//                     { key: "power", label: "Мощность", color: "#ff7300" },
//                 ]);
//                 break;
//             case "3": // График расход/квитанционный запас
//                 setGraphData(selectedPumpData.suctionReserveFlow);
//                 setLegendNames([
//                     { key: "flow", label: "Расход", color: "#82ca9d" },
//                     { key: "suctionReserve", label: "Квитанционный запас", color: "#8884d8" },
//                 ]);
//                 break;
//             default:
//                 setGraphData([]);
//                 setLegendNames([]);
//         }
//     }, [selectedPump, selectedGraphType]);
//
//     return (
//         <div>
//             <Header /><div className={styles.wrapper}>
//             <div className={styles.rectangle}>
//                 <div className={styles.header}>
//                     <h1>Результат подбора</h1>
//                 </div>
//                 <div className={styles.content}>
//                     <div className={styles.chartContainer}>
//                         <div className={styles.selectWrapper}>
//                             <select
//                                 className={styles.select}
//                                 value={selectedPump}
//                                 onChange={(e) => setSelectedPump(e.target.value)}
//                             >
//                                 <option value="SF-9050">Насосная установка SF-9050</option>
//                                 <option value="SF-5090">Насосная установка SF-5090</option>
//                                 <option value="SF-8050">Насосная установка SF-8050</option>
//                             </select>
//                         </div>
//                         <div className={styles.chart}>
//                             <Graph data={graphData} legendNames={legendNames} />
//                         </div>
//                         <div className={styles.graphSelector}>
//                             <label>
//                                 <input
//                                     type="radio"
//                                     name="graph"
//                                     value="1"
//                                     checked={selectedGraphType === "1"}
//                                     onChange={() => setSelectedGraphType("1")}
//                                 />
//                             </label>
//                             <label>
//                                 <input
//                                     type="radio"
//                                     name="graph"
//                                     value="2"
//                                     checked={selectedGraphType === "2"}
//                                     onChange={() => setSelectedGraphType("2")}
//                                 />
//                             </label>
//                             <label>
//                                 <input
//                                     type="radio"
//                                     name="graph"
//                                     value="3"
//                                     checked={selectedGraphType === "3"}
//                                     onChange={() => setSelectedGraphType("3")}
//                                 />
//                             </label>
//                         </div>
//                     </div>
//                     <div className={styles.data}>
//                         {/* Данные о выбранной установке */}
//                         <h2>Данные о приборе</h2>
//                         <p>Название: {selectedPump}</p>
//                         <p>Количество насосов:</p>
//                         <ul>
//                             <li>Рабочих: 10</li>
//                             <li>Резервных: 10</li>
//                         </ul>
//                         <p>Тип управления: частотное</p>
//                         <p>Тип электропитания: тайп-си</p>
//                         <p>Электрическая мощность каждого насоса: 90 кВт</p>
//                         <p>Суммарная мощность: 2,2</p>
//                         <p>Сила тока для каждого насоса: 4 А</p>
//                         <p>Суммарная сила тока: 12 А</p>
//                         <p>Диаметр коллектора: 2 мм</p>
//                     </div>
//                 </div>
//                 <img className={styles.arrow} src={arrow} onClick={handleArrowClick} />
//             </div>
//         </div>
//         </div>
//     );
// }
// export default SelectionResults;
import React, {useState, useEffect} from "react";
import styles from './result.module.css';
import {Header} from "../../components/UI/Header";
import arrow from "../../assets/next-page.svg";
import {useNavigate} from "react-router-dom";
import Graph from "../../components/UI/Graph";
import {useDispatch, useSelector} from "react-redux";
import {setGeneralInfo, setMaterials, setMotorData, setPumpData, setPoints} from "../../store/pumpSlice";

function SelectionResults() {
    const navigate = useNavigate();

    const [selectedInstallation, setSelectedInstallation] = useState(null); // Храним всю установку
    const [selectedPump, setSelectedPump] = useState(null); // Храним выбранный насос
    const [selectedGraphType, setSelectedGraphType] = useState("1"); // 1 - pressure/flow, 2 - power/flow, 3 - NPSH/flow
    const [graphData, setGraphData] = useState([]);
    const [legendNames, setLegendNames] = useState([]);
    const [installations, setInstallations] = useState([]); // Храним все установки
    const token = useSelector((state) => state.user.token);
    const generalInfo = useSelector((state) => state.pump.generalInfo);

    const dispatch = useDispatch();
    const handleArrowClick = (e, selectedInstallation) => {
        e.preventDefault();
        // Dispatch actions to update Redux store with data from the selected installation
        dispatch(setGeneralInfo({
            mainPump: selectedInstallation.name,
            installationType: selectedInstallation.typeInstallations,
            subType: selectedInstallation.subtype,
            liquid: selectedInstallation.coolantType,
            operatingTemperature: selectedInstallation.temperature,
            numberOfPumps: selectedInstallation.countMainPumps,
            workingPumps: selectedInstallation.countMainPumps,
            reservePumps: selectedInstallation.countSparePumps,
            controlType: selectedInstallation.controlType,
            ratedFlow: selectedInstallation.flowRate,
            ratedPressure: selectedInstallation.pressure,
            // Add more fields as necessary...
        }));

        const selectedPump = selectedInstallation.pumps[0]; // Assuming you are selecting the first pump

        dispatch(setPumpData({
            manufacturer: selectedPump.manufacturer || '',
            speed: selectedPump.speed,
            numberOfStages: selectedPump.numberOfSteps,
            maxPressure: selectedPump.maximumPressure,
            maxHead: selectedPump.maximumHead,
            impellerDiameter: selectedPump.diameter,
            // Add more fields as necessary...
        }));

        dispatch(setMotorData({
            manufacturer: selectedPump.engine.manufacturer,
            execution: selectedPump.engine.execution,
            type: selectedPump.engine.pumpType,
            power: selectedPump.engine.power,
            current: selectedPump.engine.amperage,
            voltage: selectedPump.engine.voltage,
            speed: selectedPump.engine.turnovers,
            protectionType: selectedPump.engine.typeOfProtection,
            insulationClass: selectedPump.engine.insulationClass,
            color: selectedPump.engine.color,
            // Add more fields as necessary...
        }));

        dispatch(setMaterials({
            collector: selectedPump.material.collector,
            shutOffValves: selectedPump.material.shutOffValves,
            checkValves: selectedPump.material.checkValves,
            pressureRelay: selectedPump.material.pressureSwitch,
            pressureSensors: selectedPump.material.pressureSensors,
            plugsFlanges: selectedPump.material.plugsOrFlanges,
            rack: selectedPump.material.rack,
            baseFrame: selectedPump.material.frameBase,
            pumpBody: selectedPump.material.pumpHousing,
            outerCover: selectedPump.material.externalCasing,
            // Add more fields as necessary...
        }));
        dispatch(setPoints({
            pointsPower: selectedPump.pointsPower,
            pointsPressure: selectedPump.pointsPressure,
            pointsNPSH: selectedPump.pointsNPSH,
        }))

        // Navigate to the next page
        navigate("/selection/additional_options");
    };

    // Fetch pump data from the backend
    const fetchPumpData = async () => {
        try {
            console.log(token)
            const response = await fetch("http://localhost:8080/api/simple/inst/get", {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    "typeInstallations": generalInfo.installationType,
                    "subtype": generalInfo.subType,
                    "coolantType": generalInfo.liquid,
                    "temperature": generalInfo.operatingTemperature,
                    "countMainPumps": generalInfo.workingPumps,
                    "countSparePumps": generalInfo.reservePumps,
                    "flowRate": generalInfo.ratedFlow,
                    "pressure": generalInfo.ratedPressure,
                }),
                // body: JSON.stringify({
                //     "typeInstallations": "GM",
                //     "subtype": "RELAY_CONTROL",
                //     "coolantType": "WATER",
                //     "temperature": 60,
                //     "countMainPumps": 2,
                //     "countSparePumps": 1,
                //     "flowRate": 30,
                //     "pressure": 3,
                // }),
            });
            console.log("typeInstallations: " + generalInfo.installationType,)
            console.log("subtype: " + generalInfo.subType,)
            console.log("coolantType: " + generalInfo.liquid,)
            console.log("temperature: " + generalInfo.operatingTemperature,)
            console.log("countMainPumps: " + generalInfo.workingPumps,)
            console.log("countSparePumps: " + generalInfo.reservePumps,)
            console.log("flowRate: " + generalInfo.ratedFlow,)
            console.log("pressure: " + generalInfo.ratedPressure,)

            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            const data = await response.json();
            setInstallations(data); // Store all installations
            setSelectedInstallation(data[0]); // Default to the first installation
            setSelectedPump(data[0]?.pumps[0]); // Default to the first pump of the first installation

        } catch (error) {
            console.error("Failed to fetch pump data:", error);
        }
    };

    // Call fetchPumpData on component mount
    useEffect(() => {
        fetchPumpData();
    }, []);

    // Update the graph data whenever the selected pump or graph type changes
    useEffect(() => {
        if (!selectedPump) return;

        switch (selectedGraphType) {
            case "1": // Pressure/Flow graph
                const pressureFlowData = selectedPump.pointsPressure.map(point => ({
                    name: point.id.toString(),
                    pressure: point.y,
                    flow: point.x,
                }));
                setGraphData(pressureFlowData);
                setLegendNames([
                    {key: "flow", label: "Flow", color: "#82ca9d"},
                    {key: "pressure", label: "Pressure", color: "#8884d8"},
                ]);
                break;
            case "2": // Power/Flow graph
                const powerFlowData = selectedPump.pointsPower.map(point => ({
                    name: point.id.toString(),
                    power: point.y,
                    flow: point.x,
                }));
                setGraphData(powerFlowData);
                setLegendNames([
                    {key: "flow", label: "Flow", color: "#82ca9d"},
                    {key: "power", label: "Power", color: "#ff7300"},
                ]);
                break;
            case "3": // NPSH/Flow graph
                const npshFlowData = selectedPump.pointsNPSH.map(point => ({
                    name: point.id.toString(),
                    suctionReserve: point.y,
                    flow: point.x,
                }));
                setGraphData(npshFlowData);
                setLegendNames([
                    {key: "flow", label: "Flow", color: "#82ca9d"},
                    {key: "suctionReserve", label: "NPSH", color: "#8884d8"},
                ]);
                break;
            default:
                setGraphData([]);
                setLegendNames([]);
        }
        console.log(selectedPump.pointsPressure)
    }, [selectedPump, selectedGraphType]);

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.header}>
                        <h1>Результат подбора</h1>
                    </div>
                    <div className={styles.content}>
                        <div className={styles.chartContainer}>
                            <div className={styles.selectWrapper}>
                                <select
                                    className={styles.select}
                                    value={selectedPump?.name || ""}
                                    onChange={(e) => {
                                        const selectedInst = installations.find(inst => inst.pumps[0]?.name === e.target.value);
                                        setSelectedInstallation(selectedInst);
                                        setSelectedPump(selectedInst?.pumps[0]);
                                    }}
                                >
                                    {installations.map(installation => (
                                        <option key={installation.id} value={installation.pumps[0]?.name}>
                                            {installation.name}
                                        </option>
                                    ))}
                                </select>
                            </div>
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
                        <div className={styles.data}>
                            <h2>Данные о приборе</h2>
                            <p>Название установки: {selectedInstallation?.name || "No installation selected"}</p>
                            <p>Количество насосов:</p>
                            <ul>
                                <li>Рабочих: {selectedInstallation?.countMainPumps || "No installation selected"}</li>
                                <li>Резервных: {selectedInstallation?.countSparePumps || "No installation selected"}</li>
                            </ul>
                            <p>Тип управления: {selectedInstallation?.controlType || "No installation selected"}</p>
                            <p>Тип электропитания: {selectedInstallation?.powerType || "No installation selected"}</p>
                            <p>Название насоса: {selectedPump?.name || "No pump selected"}</p>
                            <p>Суммарная мощность: {selectedPump?.engine?.power || "No pump selected"}</p>
                            <p>Сила тока для каждого насоса: {selectedPump?.engine?.amperage || "No pump selected"}</p>
                            <p>Диаметр коллектора: {selectedPump?.diameter || "No pump selected"}</p>
                        </div>
                    </div>
                    <img className={styles.arrow} src={arrow} onClick={(e) => handleArrowClick(e, selectedInstallation)}/>
                </div>
            </div>
        </div>
    );
}

export default SelectionResults;