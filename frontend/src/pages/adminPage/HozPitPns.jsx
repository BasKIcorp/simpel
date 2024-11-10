    import {useState} from "react";
    import styles from './AdminPage.module.css'
    import {useEffect} from "react";

    export const HozPitPns = ({installationData, setInstallationData}) => {

        const [temperatureError, setTemperatureError] = useState('');

        useEffect(() => {
            setInstallationData(prevData => ({
                ...prevData,
                coolantType: "WATER"
            }));
        }, [setInstallationData]);

        const handleChange = (e) => {
            const { name, value } = e.target;

            setInstallationData(prevData => {
                if (name === "coolantType") {
                    return {
                        ...prevData,
                        [name]: value,
                        concentration: value === "WATER" ? "" : "10"
                    };
                }
                return {
                    ...prevData,
                    [name]: value
                };
            });
        };

        const validateTemperature = (temp, coolant) => {
            let tempValue = parseFloat(temp);
            if(installationData.typeInstallations === "PNS"){
                if (tempValue < 4 || tempValue > 50) {
                    setTemperatureError('Для воды допустимый диапазон температуры: +4 … +50°C');
                    return false;
                }
            }else {
                if (coolant === 'WATER') {
                    if (tempValue < 4 || tempValue > 70) {
                        setTemperatureError('Для воды допустимый диапазон температуры: +4 … +70°C');
                        return false;
                    }
                } else {
                    setTemperatureError('Выберите тип теплоносителя');
                    return false;
                }
            }
            setTemperatureError('');
            return true;
        };
        return (
            <div className={styles.selectWrapper}>
                <h2 className={styles.formSubtitle}>Тип теплоносителя</h2>
                <div className={styles.radioGroup}>
                    <label>
                        <input
                            type="radio"
                            name="coolantType"
                            value="WATER"
                            onChange={handleChange}
                        /> Вода
                    </label>
                </div>

                <div className={styles.temperatureGroup}>
                    <h2 className={styles.formSubtitle}>Температура</h2>
                    <div className={styles.temperatureInputWrapper}>
                        <input
                            type="number"
                            className={styles.temperatureInput}
                            value={installationData.temperature}
                            name='temperature'
                            onChange={handleChange}
                            onBlur={() => validateTemperature(installationData.temperature, installationData.coolantType)}
                            style={temperatureError ? {borderColor: "red"} : {}}
                        />
                        <span className={styles.temperatureUnit}
                              style={temperatureError ? {color: "red"} : {}}
                        >°C</span>
                    </div>
                    {temperatureError && <p className={styles.error}>{temperatureError}</p>}
                </div>
                {installationData.typeInstallations === "PNS" && installationData.subtype !== "AFEIJP" && (<div>
                    <h2 className={styles.formSubtitle}>Тип насоса</h2>
                    <div className={styles.radioGroup}>
                        <label>
                            <input
                                type="radio"
                                name="pumpTypeForSomeInstallation"
                                value="VERTICAL"
                                onChange={handleChange}
                            /> Вертикальный
                        </label>
                    </div>
                    <br/>

                    <div className={styles.radioGroup}>
                        <label>
                            <input
                                type="radio"
                                name="pumpTypeForSomeInstallation"
                                value="HORIZONTAL"
                                onChange={handleChange}
                            /> Горизонтальный
                        </label>
                    </div>

                </div>)}
                <br/>
            </div>

        );
    };
    export default HozPitPns;