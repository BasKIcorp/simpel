import {Header} from "../../components/UI/Header";
import styles from "./additional.module.css";
import {useState} from "react";
import arrow from '../../assets/next-page.svg'
import locationArrow from '../../assets/location-arrow.svg'
import {useNavigate} from "react-router-dom";

export const AdditionalOptions = () => {
    const navigate = useNavigate();

    const handleArrowClick = async (e) => {
        e.preventDefault();
        navigate("/selection/result")
    }

    return (
        <div>
            <Header/>
            <div className={styles.wrapper}>
                <div className={styles.rectangle}>
                    <div className={styles.leftSide}>
                        <h1 className={styles.formTitle}>Дополнительно</h1>
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Пункт опции</h2>
                            <div className={styles.radioGroup}>
                                <label>
                                    <input type="radio" name="hydromodule" value="1"/> Выбор 1
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="2"/> Выбор 2
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="3"/> Выбор 3
                                </label>
                            </div>
                        </div>
                        <div className={styles.formGroup}>
                            <h2 className={styles.formSubtitle}>Пункт опции 2</h2>
                            {/*TODO: заменить на чекбоксы*/}
                            <div className={styles.radioGroup}>
                                <label>
                                    <input type="radio" name="hydromodule" value="1"/> Выбор 1
                                </label>
                                <br/>
                                <label>
                                    <input type="radio" name="hydromodule" value="2"/> Выбор 2
                                </label>
                            </div>
                        </div>
                        {/*TODO: тут главное не забыть, что тут взависимости от выбора типа установки будут разные дивы, тип гидромодуля, тип хвс пмс вот это вот все, надо запомнить или прочитать это и потом сделать обязательно*/}

                        {/*TODO: здесь не забыть сделать navigate To */}
                        <div className={styles.arrowWrapper}>
                            <img className={styles.arrow} src={arrow} alt="arrow" onClick={handleArrowClick}/>
                        </div>
                    </div>
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
