import React, {useState} from "react";
import styles from './AuthPage.module.css';
import {Header} from "../../components/UI/Header";
import {isDisabled} from "@testing-library/user-event/dist/utils";
import {useNavigate} from 'react-router-dom';
import {useDispatch} from "react-redux";
import {server_url} from "../../config";
function AuthPage() {
    const [username, setUsername] = useState('');
    const [usernameError, setUsernameError] = useState(false);
    const [password, setPassword] = useState('');
    const [passwordError, setPasswordError] = useState(false);
    const isFormValid = username !== "" && password !== "";
    const navigate = useNavigate();
    const dispatch = useDispatch();
    window.onerror = function (message, source, lineno, colno, error) {
        console.error('Ошибка:', message);
        console.error('Источник:', source);
        console.error('Строка:', lineno);
        console.error('Столбец:', colno);
        if (error) {
            console.error('Ошибка объекта:', error);
        }
        return true;
    };
    const handleUsernameChange = (e) => {
        const value = e.target.value;
        setUsername(value);

        // Регулярное выражение для проверки email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (value === "" || !emailRegex.test(value)) {
            setUsernameError(true); // Установим ошибку, если поле пустое или email некорректен
        } else {
            setUsernameError(false); // Ошибки нет, если введен корректный email
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
    const handleSubmit = async (e) => {
        e.preventDefault();
        const url = server_url + "/api/simple/auth/authenticate"
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "email": username,
                    "password": password
                })
            });

            if (response.ok) {
                const data = await response.json();
                dispatch({ type: 'set_user', payload: { username: username, token: data.token } });
                console.log(data.message);
                navigate("/selection/installation_choice", {replace: true})
            } else {
                const error = await response.json();
                alert(error.message)
            }
        } catch (error) {

            console.error('Произошла ошибка при отправке запроса:', error);
        }
    }

    const handleRegistrationClick = (e) => {
        e.preventDefault();
        navigate("/registration");
    }

    return (
        // TODO: тут нужно сделать динамическую форму, чтобы все было чики брики
        <main className={styles.authPage}>
            <Header/>
            <section className={styles.authContainer}>
                <div className={styles.formWrapper}>
                    <form className={styles.formContent}
                          onSubmit={(e) => handleSubmit(e)}
                    >
                        <h1 className={styles.formTitle}>Авторизация</h1>
                        <label htmlFor="login" className={styles['visually-hidden']}>Логин</label>
                        <input
                            id="login"
                            type="text"
                            onChange={handleUsernameChange}
                            className={`${styles.formInput} ${usernameError ? styles.errorInput : ''}`}
                            placeholder="E-mail"
                        />
                        {usernameError && <div className={styles.errorText}>Введите E-mail</div>}
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
                            Нет аккаунта? <strong  onClick={handleRegistrationClick}>Зарегистрироваться</strong>
                        </p>
                        {/*TODO: Сделать ссылку из слов зарегестрироваться, также сделать аналогичную страницу для реги*/}
                        <button
                            type="submit"
                            className={`${styles.submitButton} ${!isFormValid ? styles.disabledButton : ''} `}
                            disabled={!isFormValid}
                        >Войти</button>
                    </form>
                </div>
            </section>
        </main>
    );
}

export default AuthPage;