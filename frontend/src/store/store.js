import {combineReducers, createStore} from "redux";
import {userReducer} from "./userReducer";
import pumpReducer from './pumpSlice';


const rootReducer = combineReducers({
    user: userReducer,
    pump: pumpReducer
});

export const store = createStore(rootReducer);

// Подписка на изменения состояния и сохранение токена в localStorage
store.subscribe(() => {
    const currentState = store.getState();
    localStorage.setItem("token", JSON.stringify(currentState.user)); // Сохраняйте только состояние пользователя
});