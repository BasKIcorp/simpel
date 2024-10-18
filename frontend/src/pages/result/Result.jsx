import React, {useEffect, useState} from "react";
import styles from './result.module.css';
import {Header} from "../../components/UI/Header";
import Graph from "../../components/UI/Graph";
import testData from "../selection_results/test_data.json";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";

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
    const options = useSelector((state) => state.pump.options)
    const token = useSelector((state) => state.user.token)
    const navigate = useNavigate;
    const remoteControlNames = {
        freeCooling: 'Режим "фрикулинга" /сниженной нагрузки',
        remoteStart: 'Удаленный пуск',
        errorSignal: 'Сигнал ошибки/аварии насоса',
        pumpRunningSignal: 'Сигнал работы насоса',
        remoteSwitch: 'Удаленное переключение режимов зима/лето',
        modbusRTU: 'ModBus RTU',
        modbusTCP: 'Modbus TCP',
        gprsModule: 'Модуль GPRS оповещения',
        softStart: 'Плавный пуск'
    };

    const executionOptions = {
        1: 'Стандартное',
        2: 'В уличном кожухе',
        3: 'В утепленном кожухе с обогревом',
        4: 'Арктическое',
    };

    const collectorMaterialOptions = {
        1: 'AISI304',
        2: 'AISI316',
        3: 'Окрашенная углеродистая сталь',
        4: 'Сталь 20',
    };

    const connectionTypeOptions = {
        1: 'грувлок (victaulic)',
        2: 'фланцевое',
    };

    const vibrationSupportsOptions = {
        1: 'нет',
        2: 'да',
    };
    const vibroCompensators = {
        1: 'да',
        2: 'нет'
    }

    const filterOptions = {
        1: 'нет',
        2: '1 на коллекторе',
        3: 'на каждый насос',
    };

    const membraneTankOptions = {
        0: 'нет',
        1: '8л',
        2: '12л',
        3: '18л',
        4: '24л',
        5: '35л',
        6: '35л (отдельностоящий)',
        7: '50л',
        8: '50л (отдельностоящий)',
        9: '80л',
        10: '80л (отдельностоящий)',
        11: '100л',
        12: '100 л',
        13: '200 л',
        14: '300л',
        15: '500л',
        16: '750 л',
        17: '1000л',
        18: '1500 л',
    };

    const bufferTankOptions = {
        0: 'нет',
        1: 'вертикальный',
        2: 'горизонтальный',
    };

    const bufferTankMaterialOptions = {
        1: 'Сталь 20',
        2: 'AISI304',
    };

    const safetyValveOptions = {
        1: 'нет',
        2: 'да',
    };

    const automaticAirVentOptions = {
        1: 'нет',
        2: 'на коллекторе',
    };

    const fillModuleOptions = {
        1: 'нет',
        2: 'с вмонтированной емкостью',
    };


    const handleImageChange = (e) => {
        setSelectedImage(e.target.value);
    };

    const getPdf = async () =>{ // - тут пдфочку получить когда никита буфера накачает
        try {
            const request = JSON.stringify({
                "installationId": generalInfo.installationId ,
                "typeInstallations": generalInfo.installationType,
                "subtype": generalInfo.subType,
                "flowRate": parseInt(generalInfo.ratedFlow),
                "pressure": parseInt(generalInfo.ratedPressure),
                "options":{
                    "execution": options.execution,
                    "vibrationMounts": options.vibrationSupports,
                    "collector": options.collectorMaterial,
                    "flangesOrGrooveLock": options.connectionType,
                    "filter": options.filter,
                    "expansionTank": options.membraneTank,
                    "bufferTank": options.bufferTank,
                    "bufferTankSize": options.bufferTankVolume,
                    "bufferTankType": options.bufferTankMaterial,
                    "fuse": options.pressureSetting,
                    "airVent": options.automaticAirVent,
                    "makeUpMamOrPap": options.fillModule,
                    "pressureMamOrPap": options.fillPressure,
                    "volumeMamOrPap": options.fillVolume,
                    "vibrationLock": options.vibrationCompensators
                }
            })
            const response = await fetch("http://localhost:8080/api/simple/inst/generate", {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: request
            });
            console.log(request)
            if (!response.ok ) {
                throw new Error(`Error: ${response.status}`);
            }
            const blob = await response.blob();

            const contentDisposition = response.headers.get('Content-Disposition');
            let fileName = 'download.pdf';  // Стандартное имя файла, если не удалось извлечь
            console.log(contentDisposition)
            if (contentDisposition) {
                const fileNameMatch = contentDisposition.match(/filename="?([^"]+)"?/);
                if (fileNameMatch && fileNameMatch[1]) {
                    fileName = fileNameMatch[1];  // Извлекаем имя файла из заголовка
                }
            }

            // Создаем ссылку на файл для скачивания
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = fileName;  // Используем либо извлеченное имя, либо стандартное
            document.body.appendChild(a);
            a.click();
            a.remove();  // Удаляем элемент после скачивания

            // Освобождаем объект URL после скачивания
            window.URL.revokeObjectURL(url);


        } catch (error) {
            console.error("Failed to fetch pump data:", error);
            // navigate(-1)
            // setTimeout(() => {
            //     alert("Не было найдено установок с такими параметрами, попробуйте изменить их");
            // }, 100);
        }
    }
    const getMailPdf = async () =>{ // - тут пдфочку получить когда никита буфера накачает
        try {
            const request = JSON.stringify({
                "installationId": generalInfo.installationId ,
                "typeInstallations": generalInfo.installationType,
                "subtype": generalInfo.subType,
                "flowRate": parseInt(generalInfo.ratedFlow),
                "pressure": parseInt(generalInfo.ratedPressure),
                "options":{
                    "execution": options.execution,
                    "vibrationMounts": options.vibrationSupports,
                    "collector": options.collectorMaterial,
                    "flangesOrGrooveLock": options.connectionType,
                    "filter": options.filter,
                    "expansionTank": options.membraneTank,
                    "bufferTank": options.bufferTank,
                    "bufferTankSize": options.bufferTankVolume,
                    "bufferTankType": options.bufferTankMaterial,
                    "fuse": options.pressureSetting,
                    "airVent": options.automaticAirVent,
                    "makeUpMamOrPap": options.fillModule,
                    "pressureMamOrPap": options.fillPressure,
                    "volumeMamOrPap": options.fillVolume,
                    "vibrationLock": options.vibrationCompensators
                }
            })
            const response = await fetch("http://localhost:8080/api/simple/inst/generate", {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: request
            });
            console.log(request)
            if (!response.ok ) {
                throw new Error(`Error: ${response.status}`);
            }
            console.log(await response)


        } catch (error) {
            console.error("Failed to fetch pump data:", error);
            // navigate(-1)
            // setTimeout(() => {
            //     alert("Не было найдено установок с такими параметрами, попробуйте изменить их");
            // }, 100);
        }
    }


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
                        <button className={styles.button} onClick={getPdf}>
                            Скачать pdf
                        </button>
                        <button className={styles.button} onClick={getMailPdf}>
                            Pdf на e-mail
                        </button>
                        <div className={styles.infoContainer}>
                            <div className={styles.infoBlock}>
                                <strong>Стоимость установки:</strong>
                                <span>{generalInfo.price}</span>
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
                                    <td>{generalInfo.liquid === 'WATER' ? 'Вода'  : 'Писечка'}</td>
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
                                <tr>
                                    <td className={styles.tableTitle}>Опции</td>
                                    <td className={styles.tableTitle}>Значения</td>
                                </tr>
                                <tr>
                                    <td>Исполнение</td>
                                    <td>{executionOptions[options.execution]}</td>
                                </tr>
                                <tr>
                                    <td>Материал коллекторов</td>
                                    <td>{collectorMaterialOptions[options.collectorMaterial]}</td>
                                </tr>
                                {generalInfo.installationType === "GM" && (<tr>
                                    <td>Тип подключения</td>
                                    <td>{connectionTypeOptions[options.connectionType]}</td>
                                </tr>)}
                                <tr>
                                    <td>Виброопоры</td>
                                    <td>{vibrationSupportsOptions[options.vibrationSupports]}</td>
                                </tr>
                                <tr>
                                    <td>Виброкомпенсаторы</td>
                                    <td>{vibroCompensators[options.vibrationCompensators]}</td>
                                </tr>
                                <tr>
                                    <td>Фильтр</td>
                                    <td>{filterOptions[options.filter]}</td>
                                </tr>
                                <tr>
                                    <td>Мембранный бак</td>
                                    <td>{membraneTankOptions[options.membraneTank]}</td>
                                </tr>
                                {generalInfo.installationType === "GM" && (
                                    <>
                                    <tr>
                                    <td>Буферный бак</td>
                                    <td>{bufferTankOptions[options.bufferTank]}</td>
                                </tr>
                                {options.bufferTank !== "0" && (
                                    <>
                                        <tr>
                                            <td>Материал буферного бака</td>
                                            <td>{bufferTankMaterialOptions[options.bufferTankMaterial]}</td>
                                        </tr>
                                        <tr>
                                            <td>Объем буферного бака</td>
                                            <td>{["200 л", "300 л", "500 л", "750 л", "1000 л", "1500 л", "2000 л", "2500 л", "3000 л", "3500 л", "4000 л", "50000 л"][options.bufferTankVolume]}</td>
                                        </tr>
                                    </>
                                )}
                                <tr>
                                    <td>Предохранительный клапан</td>
                                    <td>{safetyValveOptions[options.safetyValve]}</td>
                                </tr>
                                {options.safetyValve === "2" && (
                                    <tr>
                                        <td>Давление настройки</td>
                                        <td>{options.pressureSetting ? `${options.pressureSetting} бар` : 'Не задано'}</td>
                                    </tr>
                                )}
                                <tr>
                                    <td>Автоматический воздухоотводчик</td>
                                    <td>{automaticAirVentOptions[options.automaticAirVent]}</td>
                                </tr>
                                <tr>
                                    <td>Заливочный модуль</td>
                                    <td>{fillModuleOptions[options.fillModule]}</td>
                                </tr>
                                {options.fillModule === "2" && (
                                    <>
                                        <tr>
                                            <td>Давление</td>
                                            <td>{options.fillPressure} бар</td>
                                        </tr>
                                        <tr>
                                            <td>Объем</td>
                                            <td>{options.fillVolume} л</td>
                                        </tr>
                                    </>
                                )}</>)}
                                {/* Отображение опций дистанционного управления */}
                                <tr>
                                    <td>Дистанционное управление</td>
                                    <td style={{whiteSpace: 'pre-wrap'}}>
                                        {Object.entries(options.remoteControl)
                                            .filter(([key, value]) => value === true)
                                            .map(([key]) => remoteControlNames[key] || key)
                                            .join('\n') || 'нет'}
                                    </td>
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