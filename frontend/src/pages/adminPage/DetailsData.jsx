import React from "react";
import styles from './AdminPage.module.css';

const DetailsData = ({ detail, setDetail, details, setDetails }) => {
    // Добавление новой детали
    const addDetail = () => {
        setDetails([...details, { ...detail }]); // Используем шаблон из detail
    };

    // Удаление детали
    const removeDetail = (index) => {
        setDetails(details.filter((_, i) => i !== index));
    };

    // Обновление значения детали
    const updateDetail = (index, field, value) => {
        const updatedDetails = details.map((item, i) =>
            i === index ? { ...item, [field]: value } : item
        );
        setDetails(updatedDetails);
    };

    return (
        <div>
            <h2 className={styles.formSubtitle}>Детали</h2>
            {details?.map((item, index) => (
                <div key={index} className={styles.detailItem}>
                    <h3>Деталь {index + 1}</h3>
                    {Object.keys(item).map((key) => (
                        <div key={key}>
                            <h3 className={styles.formSubtitle}>{key}</h3>
                            <input
                                type="text"
                                placeholder={key}
                                value={item[key]}
                                onChange={(e) => updateDetail(index, key, e.target.value)}
                                className={styles.inputField}
                            />
                        </div>
                    ))}
                    <button
                        onClick={() => removeDetail(index)}
                        className={styles.removeButton}
                    >
                        Удалить
                    </button>
                </div>
            ))}
            <button onClick={addDetail} className={styles.addButton}>
                Добавить деталь
            </button>
        </div>
    );
};

export default DetailsData;
