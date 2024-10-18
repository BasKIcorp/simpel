import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    generalInfo: {
        installationId: '',
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
        ratedFlow: 0,
        ratedPressure: 0,
        options: '',
        totalCapacityOfJockeyPump: '',
        requiredJockeyPumpPressure: '',
        pumpTypeForSomeInstallation: '',
        price:0,
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
    points: {
        pointsPressure: {},
        pointsPower: {},
        pointsNPSH: {}
    },
    options: {
        execution: '', // Стандартное
        collectorMaterial: '', // AISI304
        connectionType: '', // грувлок (victaulic)
        vibrationSupports: '', // нет
        vibrationCompensators: '',
        filter: '', // нет
        membraneTank: '', // нет
        bufferTank: '', // нет
        bufferTankMaterial: '', // Сталь 20
        bufferTankVolume: '', // 200 л
        safetyValve: '', // нет
        pressureSetting: '',
        automaticAirVent: '', // нет
        fillModule: '', // нет
        fillPressure: '', // 4 бар
        fillVolume: '', // 200 л
        remoteControl: {
            softStart: false,
            freeCooling: false,
            remoteSwitch: false,
            modbusRTU: false,
            modbusTCP: false,
            gprsModule: false,
            remoteStart: false,
            errorSignal: false,
            pumpRunningSignal: false,
        }
    }
};

const pumpSlice = createSlice({
    name: 'pump',
    initialState,
    reducers: {
        setGeneralInfo: (state, action) => {
            state.generalInfo = {
                ...state.generalInfo,
                ...action.payload // Обновляем только переданные поля
            };
        },
        setPumpData: (state, action) => {
            state.pumpData = {
                ...state.pumpData,
                ...action.payload // Обновляем только переданные поля
            };
        },
        setMotorData: (state, action) => {
            state.motorData = {
                ...state.motorData,
                ...action.payload // Обновляем только переданные поля
            };
        },
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
        },
        setAdditionalOptions: (state, action) => {
            state.options = {
                ...state.options,
                ...action.payload // Обновляем только переданные поля
            };
        }
    }
});

export const { setGeneralInfo, setPumpData, setMotorData, setMaterials, setPoints, setAdditionalOptions } = pumpSlice.actions;

export default pumpSlice.reducer;