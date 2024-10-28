import {Header} from "../../components/UI/Header";
import styles from "./additional.module.css";
import React, {useState} from "react";
import arrow from '../../assets/next-page.svg';
import locationArrow from '../../assets/location-arrow.svg';
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {setAdditionalOptions} from "../../store/pumpSlice";

export const AdditionalOptions = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [options, setOptions] = useState({
        execution: "1", // Стандартное
        collectorMaterial: "1", // AISI304
        connectionType: "1", // грувлок (victaulic)
        vibrationSupports: "1", // нет
        vibrationCompensators: '1', // да
        filter: "1", // нет
        membraneTank: "1", // нет
        bufferTank: "1", // нет
        bufferTankMaterial: "1", // Сталь 20
        bufferTankVolume: "1", // 200 л
        safetyValve: "1", // нет
        pressureSetting: '',
        automaticAirVent: "1", // нет
        fillModule: "1", // нет
        fillPressure: "4", // 4 бар
        fillVolume: "200", // 200 л
        remoteControl: {
            freeCooling: false,
            remoteSwitch: false,
            modbusRTU: false,
            modbusTCP: false,
            gprsModule: false,
            remoteStart: true,
            errorSignal: true,
            pumpRunningSignal: true,
            softStart: false,
        }
    });
    const [error, setError] = useState("");
    const [fillError, setFillError] = useState("")
    const instType = useSelector((state) => state.pump.generalInfo.installationType)
    const handleArrowClick = (e) => {
        e.preventDefault();


        dispatch(setAdditionalOptions(options))
        navigate("/selection/result");
    };
    const handlePressureChange = (e) => {
        const value = e.target.value;

        // Обновляем значение в стейте
        setOptions((prev) => ({ ...prev, pressureSetting: value }));

        // Проверка диапазона
        if (value < 4 || value > 16) {
            setError("Значение давления должно быть от 4 до 16 бар.");
        } else {
            setError("");
        }
    };
    const handleFillPressureChange = (e) => {
        const value = e.target.value;

        // Обновляем значение в стейте
        setOptions((prev) => ({ ...prev, fillPressure: value }));

        // Проверка диапазона
        if (value < 4 || value > 16) {
            setFillError("Значение давления должно быть от 4 до 16 бар.");
        } else {
            setFillError("");
        }
    };
    const handleChange = (e) => {
        const {name, value} = e.target;
        console.log({name, value})
        setOptions((prev) => ({...prev, [name]: value}));
    };

    const handleCheckboxChange = (e) => {
        const {name, checked} = e.target;
        console.log({name, checked})
        setOptions((prev) => ({...prev, remoteControl: {...prev.remoteControl, [name]: checked}}));
    };

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    {instType === "GM" && (<div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Дополнительно</h1>

                        {/* Конструктивные особенности */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Конструктивные особенности</h2>

                            {/* Исполнение */}
                            <h3>Исполнение:</h3>
                            {["Стандартное", "В уличном кожухе", "В утепленном кожухе с обогревом", "Арктическое"].map((option, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="execution"
                                            value={(index + 1).toString()}
                                            checked={options.execution === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {option}
                                    </label>
                                    <br/> {/* Adds space between radio buttons */}
                                </>
                            ))}
                            <br/>
                            {/* Материал коллекторов */}
                            <h3>Материал коллекторов:</h3>
                            {["AISI304", "AISI316", "Окрашенная углеродистая сталь"].map((material, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="collectorMaterial"
                                            value={(index + 1).toString()}
                                            checked={options.collectorMaterial === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {material}
                                    </label>
                                    <br/>
                                </>
                            ))}
                            <br/>
                            {/* Тип подключения */}
                            <h3>Тип подключения:</h3>
                            {["грувлок (victaulic)", "фланцевое"].map((type, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="connectionType"
                                            value={(index + 1).toString()}
                                            checked={options.connectionType === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {type}
                                    </label>
                                    <br/>
                                </>
                            ))}
                            <br/>
                            {/* Виброопоры */}
                            <h3>Виброопоры:</h3>
                            {["нет", "да"].map((support, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="vibrationSupports"
                                            value={(index + 1).toString()}
                                            checked={options.vibrationSupports === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {support}
                                    </label>
                                    <br/>
                                </>
                            ))}
                        </div>

                        {/* Дополнительное оборудование */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Дополнительное оборудование</h2>

                            {/* Фильтр */}
                            <h3>Фильтр:</h3>
                            {["нет", "1 на коллекторе", "на каждый насос"].map((filter, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="filter"
                                            value={(index + 1).toString()}
                                            checked={options.filter === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {filter}
                                    </label>
                                    <br/>
                                </>
                            ))}
                            <br/>
                            {/* Мембранный бак */}
                            <h3>Мембранный бак:</h3>
                            <select name="membraneTank" value={options.membraneTank} onChange={handleChange}
                                    className={styles.select}>
                                {["нет", "8л", "24л", "35л", "550л", "80л", "100л", "200л", "300л", "500л", "750л", "1000л", "1500л"].map((size, index) => (
                                    <option key={index} value={(index).toString()}>{size}</option>
                                ))}
                            </select>
                            {/* Буферный бак */}
                            <h3>Буферный бак:</h3>
                            <select name="bufferTank" value={options.bufferTank} onChange={handleChange}
                                    className={styles.select}>
                                {["нет", "вертикальный", "горизонтальный"].map((tank, index) => (
                                    <option key={index} value={(index).toString()}>{tank}</option>
                                ))}
                            </select>
                            {options.bufferTank !== "0" && (
                                <>
                                    <h4>Материал бака:</h4>
                                    {["Сталь 20", "AISI304"].map((material, index) => (
                                        <>
                                            <label key={index} className={styles.radioLabel}>
                                                <input
                                                    type="radio"
                                                    name="bufferTankMaterial"
                                                    value={(index + 1).toString()}
                                                    checked={options.bufferTankMaterial === (index + 1).toString()}
                                                    onChange={handleChange}
                                                    className={styles.radioInput}
                                                /> {material}
                                            </label>
                                            <br/>
                                        </>
                                    ))}
                                    <h4>Объем буферного бака:</h4>
                                    <select name="bufferTankVolume" value={options.bufferTankVolume}
                                            onChange={handleChange} className={styles.select}>
                                        {["200 л", "300 л", "500 л", "750 л", "1000 л", "1500 л", "2000 л", "2500 л", "3000 л", "3500 л", "4000 л", "50000 л"].map((volume, index) => (
                                            <option key={index} value={(index).toString()}>{volume}</option>
                                        ))}
                                    </select>
                                </>
                            )}

                            {/* Предохранительный клапан */}
                            <h3 style={{marginTop: 10}}>Предохранительный клапан:</h3>
                            {["нет", "да"].map((valve, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="safetyValve"
                                            value={(index + 1).toString()}
                                            checked={options.safetyValve === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {valve}
                                    </label>
                                    <br/>
                                </>
                            ))}
                            {options.safetyValve === "2" && (
                                <><h5>Давление настройки (бар)</h5>
                                    <input
                                        type="number"
                                        placeholder="Давление настройки (бар)"
                                        value={options.pressureSetting}
                                        // onChange={(e) => setOptions((prev) => ({
                                        //     ...prev,
                                        //     pressureSetting: e.target.value
                                        // }))}
                                        onChange={handlePressureChange}
                                        className={styles.temperatureInput}
                                        style={error ? {borderColor: "red"} : {}}
                                    />
                                    {error && <p style={{color: "red", fontSize: 14} }>{error}</p>}

                                </>
                            )}

                            {/* Автоматический воздухоотводчик */}
                            <h3>Автоматический воздухоотводчик:</h3>
                            {["нет", "на коллекторе"].map((airVent, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="automaticAirVent"
                                            value={(index + 1).toString()}
                                            checked={options.automaticAirVent === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {airVent}
                                    </label>
                                    <br/>
                                </>
                            ))}

                            {/* Заливочный модуль */}
                            <h3>Заливочный модуль:</h3>
                            {["нет", "с вмонтированной емкостью"].map((fillModule, index) => (
                                <>
                                    <label key={index} className={styles.radioLabel}>
                                        <input
                                            type="radio"
                                            name="fillModule"
                                            value={(index + 1).toString()}
                                            checked={options.fillModule === (index + 1).toString()}
                                            onChange={handleChange}
                                            className={styles.radioInput}
                                        /> {fillModule}
                                    </label>
                                    <br/>
                                </>
                            ))}
                            <br/>
                            {options.fillModule === "2" && (
                                <>
                                    <h5>Давление (бар)</h5>
                                    <input
                                        type="number"
                                        placeholder="Давление (бар)"
                                        value={options.fillPressure}
                                        // onChange={(e) => setOptions((prev) => ({
                                        //     ...prev,
                                        //     fillPressure: e.target.value
                                        // }))}
                                        onChange={handleFillPressureChange}
                                        className={styles.anotherInput}
                                        style={fillError ? {borderColor: "red"} : {}}
                                    />
                                    {fillError && <p style={{color: "red", fontSize: 14} }>{fillError}</p>}
                                    <h5>Объем (л)</h5>
                                    <input
                                        type="number"
                                        placeholder="Объем (л)"
                                        value={options.fillVolume}
                                        onChange={(e) => setOptions((prev) => ({...prev, fillVolume: e.target.value}))}
                                        className={styles.anotherInput}
                                    />

                                </>
                            )}
                        </div>
                        {/* Управление и Диспетчеризация */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Управление и Диспетчеризация</h2>
                            {/* Режим фрикулинга */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="freeCooling"
                                        defaultChecked={false}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Режим "фрикулинга" / сниженной нагрузки
                                </label>
                            </div>

                            {/* Удаленный пуск */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="remoteStart"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Удаленный пуск
                                </label>
                            </div>

                            {/* Сигнал ошибки/аварии насоса */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="pumpErrorSignal"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Сигнал ошибки/аварии насоса
                                </label>
                            </div>

                            {/* Сигнал работа насоса */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="pumpWorkingSignal"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Сигнал работа насоса
                                </label>
                            </div>
                            {/* Удаленное переключение */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="remoteSwitch"
                                        defaultChecked={false}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Удаленное переключение режимов зима/лето
                                </label>
                            </div>

                            {/* ModBus RTU */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="modbusRTU"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> ModBus RTU
                                </label>
                            </div>

                            {/* ModBus TCP */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="modbusTCP"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> ModBus TCP
                                </label>
                            </div>

                            {/* Модуль GPRS оповещения */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="gprsModule"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Модуль GPRS оповещения
                                </label>
                            </div>
                        </div>
                        <img className={styles.arrow} src={arrow}
                             onClick={(e) => handleArrowClick(e)}/>
                    </div>)}
                    {instType === "HOZPIT" && (<div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Дополнительно</h1>

                        {/* Конструктивные особенности */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Конструктивные особенности</h2>

                            {/* Исполнение */}
                            <h3>Исполнение:</h3>
                            {[
                                {label: "Стандартное", value: "1"},
                                {
                                    label: "В уличном кожухе",
                                    value: "2",
                                    description: "(эксплуатация на улице, температура выше 0)"
                                },
                                {
                                    label: "В утепленном кожухе с обогревом",
                                    value: "3",
                                    description: "(для зимы до -30)"
                                },
                                {
                                    label: "Арктическое",
                                    value: "4",
                                    description: "(для эксплуатации при температурах до -60)"
                                }
                            ].map(({label, value, description}, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="execution"
                                        value={value}
                                        checked={options.execution === value}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {label} {description}
                                    <br/>
                                </label>
                            ))}
                            <br/>

                            {/* Материал коллекторов */}
                            <h3>Материал коллекторов:</h3>
                            {["AISI304", "AISI316"].map((material, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="collectorMaterial"
                                        value={(index + 1).toString()}
                                        checked={options.collectorMaterial === (index + 1).toString()}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {material}<br/>
                                </label>
                            ))}
                            <br/>

                            {/* Виброкомпенсаторы */}
                            <h3>Виброкомпенсаторы:</h3>
                            {["да", "нет"].map((support, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="vibrationCompensators"
                                        value={(index + 1).toString()}
                                        checked={options.vibrationCompensators === (index + 1).toString()}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {support}<br/>
                                </label>
                            ))}
                            <br/>

                            {/* Виброопоры */}
                            <h3>Виброопоры:</h3>
                            {["нет", "да"].map((support, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="vibrationSupports"
                                        value={(index + 1).toString()}
                                        checked={options.vibrationSupports === (index + 1).toString()}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {support}<br/>
                                </label>
                            ))}
                        </div>

                        {/* Дополнительное оборудование */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Дополнительное оборудование</h2>

                            {/* Фильтр */}
                            <h3>Фильтр:</h3>
                            {["нет", "1 на коллекторе", "на каждый насос"].map((filter, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="filter"
                                        value={(index + 1).toString()}
                                        checked={options.filter === (index + 1).toString()}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {filter}<br/>
                                </label>
                            ))}
                            <br/>

                            {/* Мембранный бак */}
                            <h3>Мембранный бак:</h3>
                            <select name="membraneTank" value={options.membraneTank} onChange={handleChange}
                                    className={styles.select}>
                                {["нет", "12л", "18л", "24л", "35л (отдельностоящий)", "50л (отдельностоящий)"].map((size, index) => (
                                    <option key={index} value={(index).toString()}>{size}</option>
                                ))}
                            </select>
                        </div>

                        {/* Управление и Диспетчеризация */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Управление и Диспетчеризация</h2>

                            {/* Удаленный пуск */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="remoteStart"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Удаленный пуск
                                </label>
                            </div>

                            {/* Сигнал ошибки/аварии насоса */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="pumpErrorSignal"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Сигнал ошибки/аварии насоса
                                </label>
                            </div>

                            {/* Сигнал работа насоса */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="pumpWorkingSignal"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Сигнал работа насоса
                                </label>
                            </div>

                            {/* ModBus RTU */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="modbusRTU"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> ModBus RTU
                                </label>
                            </div>

                            {/* ModBus TCP */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="modbusTCP"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> ModBus TCP
                                </label>
                            </div>

                            {/* Модуль GPRS оповещения */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="gprsModule"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Модуль GPRS оповещения
                                </label>
                            </div>
                        </div>
                        <br/>
                        <img className={styles.arrow} src={arrow} onClick={(e) => handleArrowClick(e)}/>
                    </div>)}
                    {instType === "PNS" && (<div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Дополнительно</h1>

                        {/* Конструктивные особенности */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Конструктивные особенности</h2>

                            {/* Исполнение */}
                            <h3>Исполнение:</h3>
                            {[
                                {label: "Стандартное", value: "1"},
                                {
                                    label: "В уличном кожухе",
                                    value: "2",
                                    description: "(эксплуатация на улице, температура выше 0)"
                                },
                                {
                                    label: "В утепленном кожухе с обогревом",
                                    value: "3",
                                    description: "(для зимы до -30)"
                                },
                                {
                                    label: "Арктическое",
                                    value: "4",
                                    description: "(для эксплуатации при температурах до -60)"
                                }
                            ].map(({label, value, description}, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="execution"
                                        value={value}
                                        checked={options.execution === value}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {label} {description}
                                    <br/>
                                </label>
                            ))}
                            <br/>

                            {/* Материал коллекторов */}
                            <h3>Материал коллекторов:</h3>
                            {[
                                {name: "AISI304", value: "1"}, // Значение для AISI304
                                {name: "Сталь 20", value: "4"} // Значение для Сталь 20
                            ].map((material, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="collectorMaterial"
                                        value={material.value} // Используем material.value
                                        checked={options.collectorMaterial === material.value}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {material.name}<br/>
                                </label>
                            ))}
                            <br/>

                            {/* Виброкомпенсаторы */}
                            <h3>Виброкомпенсаторы:</h3>
                            {["да", "нет"].map((support, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="vibrationCompensators"
                                        value={(index + 1).toString()}
                                        checked={options.vibrationCompensators === (index + 1).toString()}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {support}<br/>
                                </label>
                            ))}
                            <br/>

                            {/* Виброопоры */}
                            <h3>Виброопоры:</h3>
                            {["нет", "да"].map((support, index) => (
                                <label key={index} className={styles.radioLabel}>
                                    <input
                                        type="radio"
                                        name="vibrationSupports"
                                        value={(index + 1).toString()}
                                        checked={options.vibrationSupports === (index + 1).toString()}
                                        onChange={handleChange}
                                        className={styles.radioInput}
                                    /> {support}<br/>
                                </label>
                            ))}
                        </div>

                        {/* Дополнительное оборудование */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Дополнительное оборудование</h2>
                            {/* Мембранный бак */}
                            <h3>Мембранный бак:</h3>
                            <select name="membraneTank" value={options.membraneTank} onChange={handleChange}
                                    className={styles.select}>
                                {["нет", "12л", "18л", "24л", "35л (отдельностоящий)", "50л (отдельностоящий)", "80л (отдельностоящий)", "100л (отдельностоящий)"].map((size, index) => (
                                    <option key={index} value={(index).toString()}>{size}</option>
                                ))}
                            </select>
                        </div>

                        {/* Управление и Диспетчеризация */}
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Управление и Диспетчеризация</h2>
                            {/* Плавный пуск */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="softStart"
                                        defaultChecked={false}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Плавный пуск (рекомендовано для насосв мощностью выше 37 квт)
                                </label>
                            </div>
                            {/* Удаленный пуск */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="remoteStart"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Удаленный пуск
                                </label>
                            </div>

                            {/* Сигнал ошибки/аварии насоса */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="pumpErrorSignal"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Сигнал ошибки/аварии насоса
                                </label>
                            </div>

                            {/* Сигнал работа насоса */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="pumpWorkingSignal"
                                        defaultChecked={true}
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Сигнал работа насоса
                                </label>
                            </div>

                            {/* ModBus RTU */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="modbusRTU"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> ModBus RTU
                                </label>
                            </div>

                            {/* ModBus TCP */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="modbusTCP"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> ModBus TCP
                                </label>
                            </div>

                            {/* Модуль GPRS оповещения */}
                            <div className={styles.checkboxGroup}>
                                <label className={styles.checkboxLabel}>
                                    <input
                                        type="checkbox"
                                        name="gprsModule"
                                        onChange={handleCheckboxChange}
                                        className={styles.checkboxInput}
                                    /> Модуль GPRS оповещения
                                </label>
                            </div>
                        </div>
                        <br/>
                        <img className={styles.arrow} src={arrow} onClick={(e) => handleArrowClick(e)}/>
                    </div>)}

                    {/*TODO: выделить правую часть в отедльный ui компонент*/}
                    <div className={styles.rightSide}>
                        <div className={styles.titlesWrapper}>
                            <div className={`${styles.titleWithArrow}`}>
                                <h2>Установка</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Параметры прибора</h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат подбора
                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.currentLocation}>
                                <h2>Дополнительные опции

                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Данные об объекте
                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                            <div className={styles.titleWithArrow}>
                                <h2>Результат
                                </h2>
                                <img className={styles.locationArrow} src={locationArrow} alt="Стрелка"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
