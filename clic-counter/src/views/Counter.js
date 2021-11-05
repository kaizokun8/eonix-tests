import React, {useState} from 'react';

const increment = (counter) => {

    return counter + 1
}

const decrement = (counter) => {

    if (counter > 0) {
        return counter - 1;
    }

    return counter;
}

const Counter = () => {

    const [counter, setCounter] = useState(0);

    return (
        <div>
            <button onClick={() => setCounter(increment(counter))}>
                Incrémenter le compteur
            </button>
            <br/> <br/>
            <button onClick={() => setCounter(decrement(counter))}>
                Décrémenter le compteur
            </button>
            <br/>
            <h2>Compteur : {counter}</h2>
        </div>
    );

};

export default Counter;
