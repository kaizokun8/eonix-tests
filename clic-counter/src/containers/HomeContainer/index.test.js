import homeContainerReducer from "./reducer";
import {INCREMENT, DECREMENT} from "./constant";

test('given initial state with counter at 0, when action type is increment, should return 1', () => {
    expect(homeContainerReducer(undefined, {type: INCREMENT})).toEqual({counter: 1});
});

test('given state with counter at 1, when action type is decrement, should return 0', () => {
    let previousState = {counter: 1};
    expect(homeContainerReducer(previousState, {type: DECREMENT})).toEqual({counter: 0});
});

test('given state with counter at 0, when action type is decrement, should return 0', () => {
    let previousState = {counter: 0};
    expect(homeContainerReducer(previousState, {type: DECREMENT})).toEqual({counter: 0});
});

