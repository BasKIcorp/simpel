import React from 'react';
import {LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ReferenceDot} from 'recharts';

const Graph = ({ data = [], legendNames = [], cords= {} }) => {

    return (
        <div>
            {data.length === 0 ? (
                <div>No data available</div>
            ) : (
                <LineChart width={500} height={400} data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" strokeWidth={2}/>
                    <YAxis strokeWidth={2}/>
                    <Tooltip />
                    {/* Используем компонент Legend и передаем русские названия */}
                    <Legend
                        payload={legendNames.map((item) => ({
                            value: item.label, // Русское название для отображения
                            type: 'line',
                            id: item.key, // Ключ для привязки данных
                            color: item.color
                        }))}
                    />
                    {legendNames.map((name, index) => (
                        <Line key={index} type="monotone" dataKey={name.key} stroke={name.color} strokeWidth={2} dot={false}/>
                    ))}
                    {cords.render && <ReferenceDot
                        x={cords.x} // Значение X
                        y={cords.y} // Значение Y
                        // x={"4"}
                        // y={"4"}
                        r={4} // Радиус точки
                        fill="red" // Цвет точки
                        stroke="none"
                    />}
                </LineChart>
            )}
        </div>
    );
};

export default Graph;