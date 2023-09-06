import { Dispatch, SetStateAction } from "react";
import DataType from "./DataType";

export default interface DataContextType {
  data: DataType;
  setData: Dispatch<SetStateAction<DataType>>;
}
