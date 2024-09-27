import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    generalInfo: {
        mainPump: '',
        installationType: '',
        subType: '',
        liquid: '',
        operatingTemperature: '',
        pumpType: '',
        numberOfPumps: 0,
        workingPumps: 0,
        reservePumps: 0,
        controlType: '',
        ratedFlow: '',
        ratedPressure: '',
        options: '',
        totalCapacityOfJockeyPump: '',
        requiredJockeyPumpPressure: ''
    },
    pumpData: {
        manufacturer: '',
        speed: '',
        numberOfStages: '',
        maxPressure: '',
        maxHead: '',
        impellerDiameter: ''
    },
    motorData: {
        manufacturer: '',
        execution: '',
        type: '',
        power: '',
        current: '',
        voltage: '',
        speed: '',
        protectionType: '',
        insulationClass: '',
        color: ''
    },
    materials: {
        collector: '',
        shutOffValves: '',
        checkValves: '',
        pressureRelay: '',
        pressureSensors: '',
        plugsFlanges: '',
        rack: '',
        baseFrame: '',
        pumpBody: '',
        outerCover: ''
    },
    points:{
        pointsPressure: {},
        pointsPower: {},
        pointsNPSH: {}
    }
};

const pumpSlice = createSlice({
    name: 'pump',
    initialState,
    reducers: {
        // Для обновления только части полей generalInfo
        setGeneralInfo: (state, action) => {
            state.generalInfo = {
                ...state.generalInfo,
                ...action.payload // Обновляем только переданные поля
            };
        },
        // Для обновления только части полей pumpData
        setPumpData: (state, action) => {
            state.pumpData = {
                ...state.pumpData,
                ...action.payload // Обновляем только переданные поля
            };
        },
        // Для обновления только части полей motorData
        setMotorData: (state, action) => {
            state.motorData = {
                ...state.motorData,
                ...action.payload // Обновляем только переданные поля
            };
        },
        // Для обновления только части полей materials
        setMaterials: (state, action) => {
            state.materials = {
                ...state.materials,
                ...action.payload // Обновляем только переданные поля
            };
        },
        setPoints: (state, action) => {
            state.points = {
                ...state.points,
                ...action.payload // Обновляем только переданные поля
            };
        }
    }
});

export const { setGeneralInfo, setPumpData, setMotorData, setMaterials, setPoints } = pumpSlice.actions;

export default pumpSlice.reducer;