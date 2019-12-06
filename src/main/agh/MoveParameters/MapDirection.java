package agh.MoveParameters;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    @Override
    public String toString(){
        switch (this){
            case NORTH:
                 return "N";
            case NORTH_EAST:
                return "NE";
            case EAST:
                return "E";
            case SOUTH_EAST:
                return "SE";
            case SOUTH:
                return "S";
            case SOUTH_WEST:
                return "SW";
            case WEST:
                return "W";
            case NORTH_WEST:
                return "NW";
            default:
                return "Error";
        }
    }
    public MapDirection next(){
        switch (this){
            case NORTH:
                return NORTH_EAST;
            case NORTH_EAST:
                return EAST;
            case EAST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return SOUTH;
            case SOUTH:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return WEST;
            case WEST:
                return NORTH_WEST;
            case NORTH_WEST:
                return NORTH;
            default:
                return NORTH;
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH:
                return NORTH_WEST;
            case NORTH_EAST:
                return NORTH;
            case EAST:
                return NORTH_EAST;
            case SOUTH_EAST:
                return EAST;
            case SOUTH:
                return SOUTH_EAST;
            case SOUTH_WEST:
                return SOUTH;
            case WEST:
                return SOUTH_WEST;
            case NORTH_WEST:
                return WEST;
            default:
                return NORTH;
        }
    }

    public Vector2d toUnitVector(){
        switch (this){
            case NORTH:
                return new Vector2d(0, 1);
            case NORTH_EAST:
                return  new Vector2d(1, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTH_EAST:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTH_WEST:
                return new Vector2d(-1, -1);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTH_WEST:
                return new Vector2d(-1, 1);
            default:
                return new Vector2d(0, 0);
        }
    }

}