import React, {useState} from "react";
import styles from './AuthPage.module.css';
import {Header} from "../../components/UI/Header";
import {isDisabled} from "@testing-library/user-event/dist/utils";
import {useNavigate} from 'react-router-dom';
import {useDispatch} from "react-redux";
import {server_url} from "../../config";
import eye from "../../assets/eye.svg"
import closeEye from "../../assets/close_eye.svg"
function AuthPage() {
    const [username, setUsername] = useState('');
    const [usernameError, setUsernameError] = useState(false);
    const [password, setPassword] = useState('');
    const [passwordError, setPasswordError] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
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
                dispatch({ type: 'set_user', payload: { username: username, token: data.token, role: data.role } });

                console.log(data.message);


                if (data.role === 'ADMIN') {
                    navigate("/adminPage", { replace: true });
                } else {
                    navigate("/selection/installation_choice", { replace: true });
                }
            } else {
                const error = await response.json();
                alert(error.message);
            }
        } catch (error) {
            console.error('Произошла ошибка при отправке запроса:', error);
        }
    };

    const handleRegistrationClick = (e) => {
        e.preventDefault();
        navigate("/registration");
    }
    const togglePasswordVisibility = () => {
        setShowPassword(prevState => !prevState);
    };
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
                        <label htmlFor="password" className={styles['visually-hidden']}>Пароль

                        </label>
                        <div className={styles.passwordWrapper}>
                            <input
                                id="password"
                                type={showPassword ? "text" : "password"}
                                className={`${styles.formInput} ${passwordError ? styles.errorInput : ''}`}
                                placeholder="Пароль"
                                onChange={handlePasswordChange}
                            />
                            <div className={styles.passwordIcon} onClick={togglePasswordVisibility}>
                                <img src={showPassword ? eye : closeEye}
                                     alt="Toggle Password Visibility"/>
                            </div>
                        </div>
                        {passwordError && <div className={styles.errorText}>Введите пароль</div>}
                        <p className={styles.registerLink}>
                            Нет аккаунта? <strong onClick={handleRegistrationClick}>Зарегистрироваться</strong>
                        </p>
                        {/*TODO: Сделать ссылку из слов зарегестрироваться, также сделать аналогичную страницу для реги*/}
                        <button
                            type="submit"
                            className={`${styles.submitButton} ${!isFormValid ? styles.disabledButton : ''} `}
                            disabled={!isFormValid}
                        >Войти
                        </button>
                    </form>
                </div>
            </section>
        </main>
    );
}

export default AuthPage;