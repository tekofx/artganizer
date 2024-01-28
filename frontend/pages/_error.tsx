import { NextPage, NextPageContext } from "next";
import NotFound from "./404";
interface Props {
  statusCode?: number;
}

const Error: NextPage<Props> = ({ statusCode }) => {
  return <NotFound />;
};

Error.getInitialProps = ({ res, err }: NextPageContext) => {
  const statusCode = res ? res.statusCode : err ? err.statusCode : 404;
  console.log(err);
  console.log(res);
  return { statusCode };
};

export default Error;
