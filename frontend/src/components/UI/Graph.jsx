import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ReferenceDot } from 'recharts';

const Graph = ({ data = [], legendNames = [], cords}) => {

    return (
        <div>
            {data.length === 0 ? (
                <div>No data available</div>
            ) : (
                <LineChart width={500} height={400} data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="flow" strokeWidth={2}
                           type="number"
                           domain={['auto', 'auto']}
                           allowDecimals={true}
                           tickCount={10} />
                    <YAxis strokeWidth={2}/>
                    <Tooltip />
                    <Legend
                        payload={legendNames.map((item) => ({
                            value: item.label,
                            type: 'pump',
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
                    {cords.render && <ReferenceDot
                        x={cords.x} // Значение X
                        y={cords.y} // Значение Y
                        // x={"4"}
                        // y={"4"}
                        r={4} // Радиус точки
                        fill="black" // Цвет точки
                        stroke="none"
                    />}
                </LineChart>
            )}
        </div>
    );
};

export default Graph;
