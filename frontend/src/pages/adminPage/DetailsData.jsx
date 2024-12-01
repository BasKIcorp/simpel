import React, {useEffect, useState} from "react";
import styles from './AdminPage.module.css'
import {server_url} from "../../config"
import {useDispatch} from "react-redux";


const DetailsData = ({detail, setDetail ,details, setDetails  }) => {
    const dispatch = useDispatch();

    const addDetail = () => {
        setDetails([...details, { name: '', descriptionDetail: '', materialDetail: '', enDin: '', aisiAstm: '' }]);
    };

    // Удаление детали
    const removeDetail = (index) => {
        setDetails(details.filter((_, i) => i !== index));
    };

    // Обновление значения детали
    const updateDetail = (index, field, value) => {
        const updatedDetails = details.map((detail, i) =>
            i === index ? { ...detail, [field]: value } : detail
        );

        setDetails(updatedDetails);
    };

    return (
        <div >
            <h2 className={styles.formSubtitle}>Детали</h2>
            {details?.map((detail, index) => (
                <div key={index} className={styles.detailItem}>
                    <h3>Деталь {index + 1}</h3>
                    <h3 className={styles.formSubtitle}>Тип насоса</h3>
                    <input
                        type="text"
                        placeholder="Название"
                        value={detail.name}
                        onChange={(e) => updateDetail(index, 'name', e.target.value)}
                        className={styles.inputField}
                    />
                    <h3 className={styles.formSubtitle}>Тип насоса</h3>
                    <input
                        type="text"
                        placeholder="Описание"
                        value={detail.descriptionDetail}
                        onChange={(e) => updateDetail(index, 'descriptionDetail', e.target.value)}
                        className={styles.inputField}
                    />
                    <h3 className={styles.formSubtitle}>Тип насоса</h3>
                    <input
                        type="text"
                        placeholder="Материал"
                        value={detail.materialDetail}
                        onChange={(e) => updateDetail(index, 'materialDetail', e.target.value)}
                        className={styles.inputField}
                    />
                    <h3 className={styles.formSubtitle}>Тип насоса</h3>
                    <input
                        type="text"
                        placeholder="DIN"
                        value={detail.enDin}
                        onChange={(e) => updateDetail(index, 'enDin', e.target.value)}
                        className={styles.inputField}
                    />
                    <h3 className={styles.formSubtitle}>Тип насоса</h3>
                    <input
                        type="text"
                        placeholder="AISI/ASTM"
                        value={detail.aisiAstm}
                        onChange={(e) => updateDetail(index, 'aisiAstm', e.target.value)}
                        className={styles.inputField}
                    />
                    <br/>
                    <br/>
                    <button onClick={() => removeDetail(index)} className={styles.removeButton}>
                        Удалить
                    </button>
                </div>
            ))}
            <br/>
            <button onClick={addDetail} className={styles.addButton}>
                Добавить деталь
            </button>

        </div>
    );
};

export default DetailsData;