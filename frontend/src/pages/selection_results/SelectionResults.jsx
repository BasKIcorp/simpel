import React from "react";
import styles from './result.module.css';
import { Header } from "../../components/UI/Header";
import arrow from "../../assets/next-page.svg";
import {useNavigate} from "react-router-dom";

function SelectionResults() {
    const navigate = useNavigate();

    const handleArrowClick = async (e) => {
        e.preventDefault();
        navigate("/selection/device_params")
    }


    return (
        <div>
            <Header />

            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.header}>
                        <h1>Результат подбора</h1>
                    </div>
                    <div className={styles.content}>
                        <div className={styles.chartContainer}>
                            <div className={styles.selectWrapper}>
                                <select className={styles.select}>
                                    <option value="">Насосная установка SF-9050</option>
                                    <option value="graph1">График 1</option>
                                    <option value="graph2">График 2</option>
                                    <option value="graph3">График 3</option>
                                </select>
                            </div>
                            <div className={styles.chart}>
                                {/* Здесь будет ваш график */}
                                <p>График</p>
                            </div>
                            <div className={styles.graphSelector}>
                                <label>
                                    <input type="radio" name="graph" value="1"/>
                                </label>
                                <label>
                                    <input type="radio" name="graph" value="2"/>
                                </label>
                                <label>
                                    <input type="radio" name="graph" value="3"/>
                                </label>
                            </div>
                        </div>
                        <div className={styles.data}>
                            <h2>Данные о приборе</h2>
                            <p>Название: SF-5090</p>
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
                    <img className={styles.arrow} src={arrow} onClick={handleArrowClick}/>
                </div>
            </div>
        </div>
    );
}

export default SelectionResults;