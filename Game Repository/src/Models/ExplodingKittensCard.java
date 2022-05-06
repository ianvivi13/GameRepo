package Models;

public class ExplodingKittensCard implements Comparable<ExplodingKittensCard>{
	private Type type;
	
	public ExplodingKittensCard(Type type) {
		this.type = type;	
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type.toString(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Object)) {
			return false;
		}
		ExplodingKittensCard other = (ExplodingKittensCard) obj;
		
		return this.compareTo(other) == 0;
	}
	
	@Override 
	public int compareTo(ExplodingKittensCard o) {
		int cmp = this.type.compareTo(o.type);
		if (cmp != 0) {
			return cmp;
		}
		return this.type.compareTo(o.type);
	}
	
	public String getImagePath() {
		String im = "_view/images/ExplodingKittens/";
		
		switch(type) {
		case AlterTheFuture:
			im += "alter_the_future";
			break;
		case Attack:
			im += "attack";
			break;
		case BeardCat:
			im += "beard_cat";
			break;
		case Cattermelon:
			im += "cattermelon";
			break;
		case Defuse:
			im += "defuse";
			break;
		case DrawFromBottom:
			im += "draw_from_the_bottom";
			break;
		case ExplodingKitten:
			im += "exploding_kitten";
			break;
		case Favor:
			im += "favor";
			break;
		case FeralCat:
			im += "feral_cat";
			break;
		case HairyPotatoCat:
			im += "hairy_potato_cat";
			break;
		case Nope:
			im += "nope";
			break;
		case RainbowRalphingCat:
			im += "rainbow_ralphing_cat";
			break;
		case SeeTheFuture:
			im += "see_the_future";
			break;
		case Shuffle:
			im += "shuffle";
			break;
		case Skip:
			im += "skip";
			break;
		case TacoCat:
			im += "taco_cat";
			break;
		case TargetedAttack:
			im += "targeted_attack";
			break;
		default:
			im += "back";
			break;
		}
		
		return im + ".jpg";
	}
	
	public String getBackPath() {
		return "_view/images/ExplodingKittens/back.jpg";
	}
	
}
