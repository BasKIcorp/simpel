const defaultState = {
    username: "",
    token: ""
};

const persistedState = localStorage.getItem("token")
    ? JSON.parse(localStorage.getItem("token"))
    : {};

const initialState = { ...defaultState, ...persistedState };

export const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case "set_user":
            return {
                ...state,
                username: action.payload.username,
                token: action.payload.token,
                role: action.payload.role,
            };
        case "remove_user":
            return { ...state, username: "", token: "", role: "" };
        default:
            return state;
    }
};
