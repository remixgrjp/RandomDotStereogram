import java.awt.*;

class RDS extends Frame{
	public static void main( String[] args ){
		new RDS();
	}

	java.awt.event.ItemListener itemListener = new java.awt.event.ItemListener(){
		public void itemStateChanged( java.awt.event.ItemEvent e ){
			int i = ((Choice)e.getItemSelectable()).getSelectedIndex();
			if( interfaceArray.length > i ){
				function = interfaceArray[i];
				repaint();
			}
		}
	};

	/** constructor */
	RDS(){
		addWindowListener( new java.awt.event.WindowAdapter(){
			public void windowClosing( java.awt.event.WindowEvent e ){
				System.exit( 0 );
			}
		} );
		setSize( 600, 600 );
		setLayout( new FlowLayout() );
		Choice choice = new Choice();
		for( int i=interfaceArray.length; 0<i; i-- ){
			choice.add( "" + i );
		}
		choice.addItemListener( itemListener );
		add( choice );
		function = interfaceArray[0];
		setVisible( true );
	}

	public void paint( Graphics graphics ){
		super.paint( graphics );
		graphics.setColor( Color.BLACK );

		graphics.fillOval( 250, 60, 8, 8 );//focus marker
		graphics.fillOval( 350, 60, 8, 8 );
		graphics.fillOval( 250, 580, 8, 8 );
		graphics.fillOval( 350, 580, 8, 8 );

		double x0=0,y0=0;
		double x=0,z=0;
		double xmax=10,xmin=-10,ymax=10,ymin=-10,zmax=1,zmin=-1;
		double dx=(xmax-xmin)/6;
		double z0=dx/(zmax-zmin)/4;
		for( int i=3000; i>0; i-- ){
			y0 = Math.random() * (ymax-ymin) + ymin;
			z = function.calc( xmin + dx/2, y0 );
			if( z>zmax ) z=zmax;
			if( z<zmin ) z=zmin;
			x = xmin+dx+z0*z;
			x0 = (x-xmin) * Math.random() + xmin;
			do{
				graphics.fillOval( (int)(x0*25)+300, (int)(y0*25)+320, 3, 3 );//plot
				z = function.calc( x0+dx/2, y0 );
				if( z>zmax ) z=zmax;
				if( z<zmin ) z=zmin;
				x0 = x0+dx+z0*z;
			}while( x0<xmax );
		}
	}

	/** for function pointer */
	Interface function;
	interface Interface{
		public double calc( double x, double y );
	}
	Interface[] interfaceArray = {
		new Interface(){
			@Override
			public double calc( double x, double y ){
				return 5-Math.sqrt(x*x+y*y);
			}
		}
	,	new Interface(){
			@Override
			public double calc( double x, double y ){
				if( (Math.sqrt(x*x+y*y)>5) && (Math.sqrt(x*x+y*y)<8) ){
					return -y/8;
				}else{
					return y/8;
				}
			}
		}
	,	new Interface(){
			@Override
			public double calc( double x, double y ){
				if( (Math.abs(y+x)<2) || (Math.abs(y-x)<2) ){
					return -x/10;
				}else{
					return x/10;
				}
			}
		}
	,	new Interface(){
			@Override
			public double calc( double x, double y ){
				return Math.cos( x ) * Math.cos( y/2 );
			}
		}
	,	new Interface(){
			@Override
			public double calc( double x, double y ){
				return (((y/2+5) % 2)+((x/2+5) % 2))/3;
			}
		}
	};
}
