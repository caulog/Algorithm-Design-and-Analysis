public class recursive_division_algorithm{
    public static void main(String[] args){
        int[] ans = divide(0, 2);
        
        System.out.println(ans[0] + ", " + ans[1]);
    }

    public static int[] divide(int x, int y){
        int[] result = {0,0};

        if(x == 0){
            result[0] = 0;
            result[1] = 0;
            return result;
        }

        result = divide((int)(Math.floor(x/2)), y);

        int q = 2*result[0];
        int r = 2*result[1];

        if(x%2 != 0){
            r = r+1;
        }

        if(r >= y){
            r = r-y;
            q = q+1;
        }

        result[0] = q;
        result[1] = r;
        return result;
    }
}