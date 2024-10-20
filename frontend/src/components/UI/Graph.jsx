import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const Graph = ({ data = [], legendNames = [] }) => {
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
                </LineChart>
            )}
        </div>
    );
};

export default Graph;