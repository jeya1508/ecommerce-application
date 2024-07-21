import { configureStore } from "@reduxjs/toolkit";
import tokenReducer from "../feature/redux/slice/tokenSlice.ts";

export default configureStore({
  reducer: {
    tokenLoader: tokenReducer,
  },
});