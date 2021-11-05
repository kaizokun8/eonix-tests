import React from 'react';

function Home({onIncrement, onDecrement, counter}) {
    return (
        <div>
            <button onClick={onIncrement}>
                Incrémenter le compteur
            </button>
            <br/> <br/>
            <button onClick={onDecrement}>
                Décrémenter le compteur
            </button>
            <br/>
            <h2>Compteur : {counter}</h2>
        </div>
    );
};

export default Home;
