public class Func {
    public static double calculateFunc(double x, double y, double z){
        double result;
        result = Math.log10(Math.sqrt(Math.exp(x-y) + Math.pow(x, Math.abs(y)) + z))
                * (x - Math.pow(x, 3)/3 - Math.pow(x, 7));
        return result;
    }
}
