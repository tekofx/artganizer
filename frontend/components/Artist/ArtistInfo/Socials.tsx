import Social from "../../../interfaces/Social";
import SocialIcon from "../../SocialIcon";
interface SocialsProps {
  socials: Social[];
}

export default function Socials({ socials }: SocialsProps) {
  console.log(socials);
  return (
    <div>
      {socials.map((social, index) => (
        <div key={index}>
          <SocialIcon social={social} />
        </div>
      ))}
    </div>
  );
}
