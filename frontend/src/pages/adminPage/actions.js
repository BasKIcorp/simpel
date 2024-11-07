// actions.js
export const fetchPumps = () => async (dispatch) => {
    const response = await fetch('/api/simple/admin/pumps');
    const data = await response.json();
    dispatch({ type: 'FETCH_PUMPS', payload: data });
};

export const fetchEngines = () => async (dispatch) => {
    const response = await fetch('/api/simple/admin/engines');
    const data = await response.json();
    console.log(data)
    dispatch({ type: 'FETCH_ENGINES', payload: data });
};

export const saveInstallation = (values) => async (dispatch) => {
    const formData = new FormData();
    formData.append('request', JSON.stringify(values)); // Преобразуем данные в нужный формат
    formData.append('files', values.files);
    formData.append('points', JSON.stringify(values.points));

    const response = await fetch('/api/simple/admin/save', {
        method: 'POST',
        body: formData,
    });

    const data = await response.json();
    dispatch({ type: 'SAVE_INSTALLATION', payload: data });
};
