import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const Graph = ({ data = [], legendNames = []}) => {
    const combinedData = {};

    // Проходим по каждому элементу в данных
    data.forEach(item => {
        const flowValue = item.flow;

        // Если значение flow уже существует, то обновляем его
        if (combinedData[flowValue]) {
            legendNames.forEach((legend) => {
                if (item[legend.key]) {
                    combinedData[flowValue][legend.key] = item[legend.key];
                }
            });
        } else {
            // Если значение flow еще не добавлено, добавляем его
            combinedData[flowValue] = { flow: flowValue };
            legendNames.forEach((legend) => {
                if (item[legend.key]) {
                    combinedData[flowValue][legend.key] = item[legend.key];
                }
            });
        }
    });

    // Преобразуем объект в массив
    const finalData = Object.values(combinedData);
    return (
        <div>
            {data.length === 0 ? (
                <div>No data available</div>
            ) : (
                <LineChart width={500} height={400} data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="flow" strokeWidth={2}/>
                    <YAxis strokeWidth={2}/>
                    <Tooltip />
                    <Legend
                        payload={legendNames.map((item) => ({
                            value: item.label,
                            type: 'line',
                            id: item.key,
                            color: item.color
                        }))}
                    />
                    {/* Рендерим все линии */}
                    {legendNames.map((name, index) => (
                        <Line
                            connectNulls={true}
                            key={index}
                            type="monotone"
                            dataKey={name.key} // Название ключа данных для линии
                            stroke={name.color}
                            strokeWidth={2}
                            dot={false}
                        />
                    ))}
                </LineChart>
            )}
        </div>
    );
};

export default Graph;
