import React, {useState} from "react";
import styles from '../auth/AuthPage.module.css';
import {Header} from "../../components/UI/Header";
import {isDisabled} from "@testing-library/user-event/dist/utils";
import {useNavigate} from "react-router-dom";

function RegPage() {
    const [username, setUsername] = useState('');
    const [usernameError, setUsernameError] = useState(false);
    const [password, setPassword] = useState('');
    const [passwordError, setPasswordError] = useState(false);
    const isFormValid = username !== "" && password !== "";
    const navigate = useNavigate();

    const handleUsernameChange = (e) => {
        const value = e.target.value;
        setUsername(value);
        if (value === "") {
            setUsernameError(true);
        } else {
            setUsernameError(false);
        }
    };
    const handlePasswordChange = (e) => {
        const value = e.target.value;
        setPassword(value);
        if (value === "") {
            setPasswordError(true);
        } else {
            setPasswordError(false);
        }
    };
    const handleSubmit = (e) => {
        e.preventDefault();
    }

    const handleLoginClick = (e) => {
        e.preventDefault();
        navigate("/auth");
    }

    return (
        // TODO: тут нужно сделать динамическую форму, чтобы все было чики брики
        <main className={styles.authPage}>
            <Header/>
            <section className={styles.authContainer}>
                <div className={styles.formWrapper}>
                    <form className={styles.formContent}>
                        <h1 className={styles.formTitle}>Регистрация</h1>
                        <label htmlFor="login" className={styles['visually-hidden']}>Логин</label>
                        <input
                            id="login"
                            type="text"
                            onChange={handleUsernameChange}
                            className={`${styles.formInput} ${usernameError ? styles.errorInput : ''}`}
                            placeholder="Логин"
                        />
                        {usernameError && <div className={styles.errorText}>Введите имя пользователя</div>}
                        <label htmlFor="password" className={styles['visually-hidden']}>Пароль</label>
                        <input
                            id="password"
                            type="password"
                            className={`${styles.formInput} ${passwordError ? styles.errorInput : ''}`}
                            placeholder="Пароль"
                            onChange={handlePasswordChange}
                        />
                        {passwordError && <div className={styles.errorText}>Введите пароль</div>}
                        <p className={styles.registerLink}>
                            Есть аккаунт? <strong onClick={handleLoginClick}>Войти</strong>
                        </p>
                        {/*TODO: Сделать ссылку из слов зарегестрироваться, также сделать аналогичную страницу для реги*/}
                        <button
                            type="submit"
                            className={`${styles.submitButton} ${!isFormValid ? styles.disabledButton : ''} `}
                            disabled={!isFormValid}
                            onSubmit={(e) => handleSubmit(e)}
                        >Войти</button>
                    </form>
                </div>
            </section>
        </main>
    );
}

export default RegPage;