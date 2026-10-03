class Winner {
    int decideWinner(Move a, Move b)
    {
        if(a.equals(Move.Rock))
        {
            if(b.equals(Move.Scissors))
            {
                return 1;
            }
            else if(b.equals(Move.Lizard))
            {
                return 1;
            }
            else if(b.equals(Move.Rock))
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        else if(a.equals(Move.Paper))
        {
            if(b.equals(Move.Rock))
            {
                return 1;
            }
            else if(b.equals(Move.Spock))
            {
                return 1;
            }
            else if(b.equals(Move.Paper))
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        else if(a.equals(Move.Scissors))
        {
            if(b.equals(Move.Paper))
            {
                return 1;
            }
            else if(b.equals(Move.Lizard))
            {
                return 1;
            }
            else if(b.equals(Move.Scissors))
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        else if(a.equals(Move.Lizard))
        {
            if(b.equals(Move.Paper))
            {
                return 1;
            }
            else if(b.equals(Move.Spock))
            {
                return 1;
            }
            else if(b.equals(Move.Lizard))
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        else if(a.equals(Move.Spock))
        {
            if(b.equals(Move.Rock))
            {
                return 1;
            }
            else if(b.equals(Move.Scissors))
            {
                return 1;
            }
            else if(b.equals(Move.Spock))
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        return 0;
    }

}
