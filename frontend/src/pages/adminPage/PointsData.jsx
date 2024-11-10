import React, { useState } from "react";
import styles from "./AdminPage.module.css";

const PointsData = ({ points, setPoints }) => {
    const [point, setPoint] = useState({ x: "", y: "", type: "" });

    const handleAddPoint = () => {
        if (point.x && point.y && point.type) {
            setPoints([...points, point]);
            setPoint({ x: "", y: "", type: "" });
        }
    };

    const handleRemovePoint = (index) => {
        setPoints(points.filter((_, i) => i !== index));
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setPoint({ ...point, [name]: value });
    };

    return (
        <div className={styles.formContainer}>
            <h3 className={styles.title}>Добавить точку</h3>
            <div className={styles.typeCoordinateWrapper}>
                <div>
                    <label className={styles.typeLabel}>Тип </label>
                    <select
                        name="type"
                        value={point.type}
                        onChange={handleChange}
                        style={{ width: '10em' }}
                        className={styles.select}
                    >
                        <option value="">Тип</option>
                        <option value="Pressure">Pressure</option>
                        <option value="Power">Power</option>
                        <option value="Npsh">Npsh</option>
                    </select>
                </div>
                <div className={styles.coordinateWrapper}>
                    <div>
                        <input
                            type="text"
                            name="x"
                            value={point.x}
                            placeholder="X"
                            onChange={handleChange}
                            className={styles.coordinateInput}
                        />
                    </div>
                    <div>
                        <input
                            type="text"
                            name="y"
                            value={point.y}
                            placeholder="Y"
                            onChange={handleChange}
                            className={styles.coordinateInput}
                        />
                    </div>
                </div>
                <button onClick={handleAddPoint} className={styles.button}>Добавить</button>
            </div>
            <div className={styles.pointList}>
                {points.map((p, index) => (
                    <div key={index} className={styles.pointCard}>
                        <p>Тип: {p.type}</p>
                        <p>X: {p.x}</p>
                        <p>Y: {p.y}</p>
                        <button onClick={() => handleRemovePoint(index)} className={styles.button}>
                            Удалить
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default PointsData;
