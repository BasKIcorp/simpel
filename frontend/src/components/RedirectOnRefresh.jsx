import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import InstallationChoice from '../pages/installation/InstallationChoice.jsx'; // Страница выбора установки

// Компонент для обработки перенаправления при обновлении
export const RedirectOnRefresh = () => {
    const navigate = useNavigate();

    useEffect(() => {
        // Перенаправляем пользователя на страницу installation_choice при загрузке компонента
        navigate('/installation_choice');
    }, [navigate]);

    return null; // Этот компонент ничего не рендерит
};