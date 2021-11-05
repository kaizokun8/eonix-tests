import React, {useState} from 'react';

export const Counter = () => {

    const [counter, setCounter] = useState(0);

    const increment = () => {

        setCounter(counter + 1);
    }

    const decrement = () => {

        if (counter > 0) {
            setCounter(counter - 1);
        }
    }

    return (
        <div>
            <button onClick={increment}>
                Incrémenter le compteur
            </button>
            <br/> <br/>
            <button onClick={decrement}>
                Décrémenter le compteur
            </button>
            <br/>
            <h2>Compteur : {counter}</h2>
        </div>
    );

};

export default Counter;
