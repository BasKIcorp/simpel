import {useState} from "react";
import styles from './AdminPage.module.css'

export const GMFields = ({installationData, setInstallationData}) => {

    const [temperatureError, setTemperatureError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;

        setInstallationData(prevData => {
            // Устанавливаем концентрацию в 10% при выборе растворов, сбрасываем для воды
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
        if (coolant === 'WATER') {
            if (tempValue < 4 || tempValue > 70) {
                setTemperatureError('Для воды допустимый диапазон температуры: +4 … +70°C');
                return false;
            }
        } else if (coolant === 'PROPYLENE_GLYCOL' || coolant === 'ETHYLENE_GLYCOL') {
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
                <br/>
                <label>
                    <input
                        type="radio"
                        name="coolantType"
                        value="PROPYLENE_GLYCOL"
                        onChange={handleChange}
                    /> Водный раствор пропиленгликоля
                    <select className={styles.concentraitionSelect} name="concentration" onChange={handleChange}>
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
                        name="coolantType"
                        value="ETHYLENE_GLYCOL"
                        onChange={handleChange}
                    /> Водный раствор этиленгликоля
                    <select className={styles.concentraitionSelect} name="concentration" onChange={handleChange}>
                        <option value="10">10%</option>
                        <option value="20">20%</option>
                        <option value="30">30%</option>
                        <option value="40">40%</option>
                        <option value="50">50%</option>
                    </select>
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

        </div>


    );
};
export default GMFields;