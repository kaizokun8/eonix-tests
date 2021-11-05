import {increment,decrement} from '../src/views/Counter';

test('given value 0, when call increment, should return 1', () => {
    expect(increment(0)).toEqual(1);
});

test('given state with counter at 1, when action type is decrement, should return 0', () => {
    expect(decrement(1)).toEqual(0);
});

test('given state with counter at 0, when action type is decrement, should return 0', () => {
    expect(decrement(0)).toEqual(0);
});

