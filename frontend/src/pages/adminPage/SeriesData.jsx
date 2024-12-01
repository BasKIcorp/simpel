import React, {useEffect, useState} from "react";
import styles from './AdminPage.module.css'
import {server_url} from "../../config"
import {useDispatch} from "react-redux";


const SeriesData = ({series, setSeties}) => {
    const dispatch = useDispatch();

    const handleChangeFields = (e, index) => {
        const { name, value } = e.target;
        setSeties(prevData => {
            return { ...prevData, [name]: value };
        });
    };


    return (
        <div>
            <h2 className={styles.formSubtitle}>Серия</h2>
                <h3 className={styles.formSubtitle}>Название</h3>
                <input
                    type="text"
                    placeholder="Название"
                    value={series.name || ""}
                    name="name"
                    onChange={(e) => handleChangeFields(e)}
                    className={styles.radioGroup}
                />
                <h3 className={styles.formSubtitle}>Категория</h3>
                <select
                    style={{fontSize: "14px", marginTop: "9px", height: "25px"}}
                    value={series.name || ""}
                    name="categoryName"
                    onChange={(e) => handleChangeFields(e)}
                >
                    <option value="" disabled>Выберите категорию</option>
                    <option value="Водоснабжение">Водоснабжение</option>
                    <option value="Канализация и водоотведение">Канализация и водоотведение</option>
                    <option value="Отопление/Кондиционирование">Отопление/Кондиционирование</option>
                </select>
        </div>
                );
                };
export default SeriesData;